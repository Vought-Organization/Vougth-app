import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vought.databinding.FragmentEventBinding
import com.example.vought.model.Event
import com.example.vought.model.Ticket
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import com.paypal.android.sdk.payments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val PAYPAL_REQUEST_CODE = 123
    private val PAYPAL_CLIENT_ID = "Ae9NnC5s9hCMzgK5Bm1dtfHGDtKRiaWc0WteHiwQL5n6NXzmsjCm1VbO5jQuzyarrMQNXS1FEegvtqv6"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("eventId", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        val view = binding.root

        val eventId = sharedPreferences.getInt("eventId", 1)

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

        return view
    }

    private fun updateEventDetails(event: Event) {
        val nameTextView = binding.titleEvent
        val descriptionTextView = binding.textDescription

        nameTextView.text = event.nameEvent
        descriptionTextView.text = event.description
    }

    private fun setupPayPalButton(event: Event) {
        PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Defina o ambiente do PayPal (sandbox ou produção)
            .clientId(PAYPAL_CLIENT_ID) // Defina o Client ID do PayPal

        val payPalButton = binding.btnPaypal

        payPalButton.setOnClickListener {

            val service = Api.createService(RetrofitService::class.java)
            val getTicketsRequest = service.getTickets(event.idEvent)

            getTicketsRequest.enqueue(object : Callback<List<Ticket>> {
                override fun onResponse(call: Call<List<Ticket>>, response: Response<List<Ticket>>) {
                    if (response.isSuccessful) {
                        val tickets = response.body()
                        if (!tickets.isNullOrEmpty()) {
                            val selectedTicket = tickets.firstOrNull()
                            if (selectedTicket != null) {
                                performPayment(event, selectedTicket)
                            } else {
                                showToast("Ingresso não encontrado")
                            }
                        } else {
                            showToast("Não há ingressos disponíveis.")
                        }
                    } else {
                        showToast("Erro ao obter ingressos.")
                    }
                }

                override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                    showToast("Ocorreu um erro inesperado ao obter ingressos.")
                }
            })
        }
    }

    private fun performPayment(event: Event, ticket: Ticket) {
        val paypalPayment = PayPalPayment(
            ticket.priceTicket,
            "BRL",
            event.nameEvent,
            PayPalPayment.PAYMENT_INTENT_SALE
        )

        val paypalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Defina o ambiente do PayPal (sandbox ou produção)
            .clientId(PAYPAL_CLIENT_ID) // Defina o Client ID do PayPal

        val intent = Intent(requireContext(), PaymentActivity::class.java)
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
        requireContext().stopService(Intent(requireContext(), PayPalService::class.java))
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        val paypalConfig = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Defina o ambiente do PayPal (sandbox ou produção)
            .clientId(PAYPAL_CLIENT_ID) // Defina o Client ID do PayPal

        val intent = Intent(requireContext(), PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        requireContext().startService(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
