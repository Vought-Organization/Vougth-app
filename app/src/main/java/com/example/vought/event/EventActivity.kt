package com.example.vought.event

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.vought.databinding.ActivityEventBinding
import com.example.vought.model.Event
import com.example.vought.model.TicketEventData
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import com.paypal.android.sdk.payments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var eventId: Int? = null


    private val PAYPAL_REQUEST_CODE = 123
    private val PAYPAL_CLIENT_ID = "AekNogbZqfLM-DVzxKhkfHXAsC2UoZ90cdqCRBUpfyLBO8bcOmgZeX6hZ8Uvxxcs03H9Ejyyin65DFiD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("eventId", Context.MODE_PRIVATE)

        eventId = intent.getIntExtra("eventId", 0)

        val service = Api.createService(RetrofitService::class.java)
        val getEventRequest = service.getEvent()

        getEventRequest.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    if (!events.isNullOrEmpty()) {
                        val event = events.find { it.idEvent == eventId }
                        if (event != null) {
                            updateEventDetails(event)
                            setupPayPalButton(event)
                        } else {
                            Log.d("MyEventActivity", "ID do evento $eventId")
                            showToast("Evento não encontrado")
                        }
                    } else {
                        showToast("Não há eventos.")
                    }
                } else {
                    showToast("Erro ao conectar a API.")
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                showToast("Ocorreu um erro inesperado, feche o aplicativo e tente novamente.")
            }
        })
    }

    private fun updateEventDetails(event: Event) {
        val nameTextView = binding.titleEvent
        val descriptionTextView = binding.textDescription

        nameTextView.text = event.nameEvent
        descriptionTextView.text = event.description
    }

    private fun setupPayPalButton(event: Event) {
        val paypalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID)

        val payPalButton = binding.btnPaypal

        payPalButton.setOnClickListener {
            val service = Api.createService(RetrofitService::class.java)

            service.getTickets().enqueue(object : Callback<List<TicketEventData>> {
                override fun onResponse(call: Call<List<TicketEventData>>, response: Response<List<TicketEventData>>) {
                    if (response.isSuccessful) {
                        val tickets = response.body()
                        Log.d("TICKETS", "$tickets")
                        if (!tickets.isNullOrEmpty()) {
                            val selectedTicket = tickets.find { it.event?.idEvent == event.idEvent }
                            if (selectedTicket != null) {
                                performPayment(event, selectedTicket)
                            } else {
                                Log.d("SELECT TICKET", "$selectedTicket")
                                showToast("Ingresso não encontrado")
                            }
                        } else {
                            showToast("Não há ingressos disponíveis.")
                        }
                    } else {
                        showToast("Erro ao obter ingressos.")
                    }
                }

                override fun onFailure(call: Call<List<TicketEventData>>, t: Throwable) {
                    showToast("Ocorreu um erro inesperado ao obter ingressos.")
                }
            })
        }
    }

    private fun performPayment(event: Event, ticket: TicketEventData?) {
        Log.d("OBJETO TICKET:", ticket.toString())

        val paypalPayment = PayPalPayment(
            ticket?.precoIngresso?.let { BigDecimal.valueOf(it.toDouble()) },
            "BRL",
            event.nameEvent ?: "",
            PayPalPayment.PAYMENT_INTENT_SALE
        )

        val paypalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID)

        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, paypalPayment)
        startActivityForResult(intent, PAYPAL_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYPAL_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val confirm = data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                    if (confirm != null) {
                        val paymentDetails = confirm.toJSONObject().toString()
                        showToast("Pagamento confirmado")
                    }
                }
                Activity.RESULT_CANCELED -> showToast("Pagamento cancelado")
                PaymentActivity.RESULT_EXTRAS_INVALID -> showToast("Dados do pagamento inválidos")
            }
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        val paypalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID)

        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
