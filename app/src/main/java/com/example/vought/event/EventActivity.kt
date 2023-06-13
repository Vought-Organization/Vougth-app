package com.example.vought.event

import android.app.Activity
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
import com.google.android.gms.wallet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var eventId: Int? = null

    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 123
    // credenciais de acesso para o pagamento
    private val user_id = 683498511
    private val application_number = 5405315337675816
    private val integration_gateway = "CheckoutPro"
    private val merchantName = "Vought"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("eventId", MODE_PRIVATE)

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
                            setupGooglePayButton(event)
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

    private fun setupGooglePayButton(event: Event) {
        val googlePayButton = binding.btnPaypal

        googlePayButton.setOnClickListener {
            val service = Api.createService(RetrofitService::class.java)

            service.getTickets().enqueue(object : Callback<List<TicketEventData>> {
                override fun onResponse(call: Call<List<TicketEventData>>, response: Response<List<TicketEventData>>) {
                    if (response.isSuccessful) {
                        val tickets = response.body()
                        Log.d("TICKETS", "$tickets")
                        if (!tickets.isNullOrEmpty()) {
                            val selectedTicket = tickets.find { it.event?.idEvent == event.idEvent }
                            if (selectedTicket != null) {
                                startGooglePayPayment(selectedTicket)
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

    private fun startGooglePayPayment(ticket: TicketEventData) {
        val paymentDataRequestJson = "{\n" +
                "  \"apiVersion\": 2,\n" +
                "  \"apiVersionMinor\": 0,\n" +
                "  \"allowedPaymentMethods\": [\n" +
                "    {\n" +
                "      \"type\": \"CARD\",\n" +
                "      \"parameters\": {\n" +
                "        \"allowedAuthMethods\": [\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"],\n" +
                "        \"allowedCardNetworks\": [\"MASTERCARD\", \"VISA\"],\n" +
                "        \"billingAddressRequired\": true,\n" +
                "        \"billingAddressParameters\": {\n" +
                "          \"format\": \"FULL\",\n" +
                "          \"phoneNumberRequired\": true\n" +
                "        }\n" +
                "      },\n" +
                "      \"tokenizationSpecification\": {\n" +
                "        \"type\": \"PAYMENT_GATEWAY\",\n" +
                "        \"parameters\": {\n" +
                "          \"gateway\": \"sua_gateway_de_pagamento\",\n" +
                "          \"gatewayMerchantId\": \"${application_number}\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"merchantInfo\": {\n" +
                "    \"merchantId\": \"${user_id}\",\n" +
                "    \"merchantName\": \"${merchantName}\"\n" +
                "  },\n" +
                "  \"transactionInfo\": {\n" +
                "    \"totalPriceStatus\": \"FINAL\",\n" +
                "    \"totalPrice\": \"${ticket.precoIngresso}\",\n" +
                "    \"currencyCode\": \"BRL\"\n" +
                "  }\n" +
                "}"

        val paymentDataRequest = PaymentDataRequest.fromJson(paymentDataRequestJson)

        val paymentClient = createGooglePayClient()

        val paymentDataRequestCode = LOAD_PAYMENT_DATA_REQUEST_CODE
        val task = paymentClient.loadPaymentData(paymentDataRequest)

        AutoResolveHelper.resolveTask(task, this@EventActivity, paymentDataRequestCode)
    }

    private fun createGooglePayClient(): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
            .build()

        return Wallet.getPaymentsClient(this@EventActivity, walletOptions)
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        // Processar os dados do pagamento
        showToast("Pagamento bem-sucedido! ID do pagamento: ${paymentData.paymentMethodToken.token}")
    }

    private fun handlePaymentError(exception: Int?) {
        // Tratar o erro no pagamento
        showToast("Erro no pagamento: {exception?.statusCode}")
    }

    private fun showToast(message: String) {
        Toast.makeText(this@EventActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    if (data != null) {
                        val paymentData = PaymentData.getFromIntent(data)
                        // Processar os dados do pagamento
                        handlePaymentSuccess(paymentData)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // O pagamento foi cancelado pelo usuário
                }
                AutoResolveHelper.RESULT_ERROR -> {
                    if (data != null) {
                        val status = AutoResolveHelper.getStatusFromIntent(data)
                        // Tratar o erro no pagamento
                        handlePaymentError(status?.statusCode)
                    }
                }
            }
        }
    }
}
