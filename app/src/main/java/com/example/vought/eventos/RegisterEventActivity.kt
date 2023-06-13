package com.example.vought.eventos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.vought.R
import com.example.vought.databinding.ActivityRegisterEventBinding
import com.example.vought.model.Address
import com.example.vought.model.Event
import com.example.vought.model.EventRegister
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class RegisterEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEventBinding
    private lateinit var btnDatePicker: Button
    private lateinit var btnTimePicker: Button
    private lateinit var txtDate: EditText
    private lateinit var txtTime: EditText
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private lateinit var viaCepService: RetrofitService
    private lateinit var spinnerCategory: Spinner
    private val categories = arrayOf("Selecione uma categoria","show", "palestra", "teatro", "passeio", "congresso", "infantil", "standup")
    private lateinit var selectedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viaCepService = Api.createViaCepService()

        binding = ActivityRegisterEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalveEvent.setOnClickListener {
            registerEvent()
        }

        binding.btnCep.setOnClickListener {
            searchCep()
        }

        btnDatePicker = binding.btnDate
        btnTimePicker = binding.btnTime
        txtDate = binding.inDate
        txtTime = binding.inTime

        btnDatePicker.setOnClickListener {
            onClickDate()
        }
        btnTimePicker.setOnClickListener(this::onClick)

        spinnerCategory = findViewById(R.id.spinner_category_event)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Faz nada
            }
        }

        val textColor = Color.BLACK
        val spinnerAdapter = spinnerCategory.adapter
        if (spinnerAdapter != null && spinnerAdapter is ArrayAdapter<*>) {
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = object : ArrayAdapter<String>(
                applicationContext,
                android.R.layout.simple_spinner_item,
                categories
            ) {
                override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    view?.let {
                        if (it is android.widget.TextView) {
                            it.setTextColor(textColor)
                        }
                    }
                    return view
                }
            }
        }

        selectedCategory = categories[0]

    }

    private fun onClickDate() {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                txtDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun onClick(v: View) {
        if (v == btnDatePicker) {
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    txtDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        }
        if (v == btnTimePicker) {
            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMinute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                    txtTime.setText("$hourOfDay:$minute")
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }
    }

    private fun getLatLngFromAddress(address: String): LatLng? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addressList = geocoder.getFromLocationName(address, 1)
        if (addressList != null) {
            if (addressList.isNotEmpty()) {
                val location = addressList?.get(0)
                if (location != null) {
                    return LatLng(location.latitude, location.longitude)
                }
            }
        }
        return null
    }

    private fun searchCep() {
        val cep = binding.edtCepEvent.text.toString()

        val request = viaCepService.getAddress(cep)

        request.enqueue(object : Callback<Address> {
            override fun onResponse(call: Call<Address>, response: Response<Address>) {
                if (response.isSuccessful) {
                    val address = response.body()
                    if (address != null) {
                        val addressString = "${address.logradouro}, ${address.localidade}, ${address.uf}"
                        val latLng = getLatLngFromAddress(addressString)
                        if (latLng != null) {
                            val latitude = latLng.latitude
                            val longitude = latLng.longitude

                            binding.edtAddressEvent.setText(address.logradouro)
                            binding.edtCityEvent.setText(address.localidade)
                            binding.edtStateEvent.setText(address.uf)
                        } else {
                            Toast.makeText(this@RegisterEventActivity, "Endereço inválido", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@RegisterEventActivity, "Erro ao obter dados do CEP", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Address>, t: Throwable) {
                Toast.makeText(this@RegisterEventActivity, "Falha ao obter dados do CEP", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun registerEvent() {
        val date = txtDate.text.toString()
        val time = txtTime.text.toString()

        val startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d-M-yyyy"))
        val startTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

        val startDateTime = LocalDateTime.of(startDate, startTime)
        val timeDuration: EditText = binding.inTimeDuration
        val duration: Double = timeDuration.text.toString().toDouble()
        val endDateTime = startDateTime.plusHours(duration.toInt().toLong())
        val category = selectedCategory
        val cep = binding.edtCepEvent.text.toString()
        val nameEvent = binding.edtEventTitle.text.toString()
        val description = binding.edtDescriptionEvent.text.toString()
        val addressEvent = binding.edtAddressEvent.text.toString()
        val city = binding.edtCityEvent.text.toString()
        val state = binding.edtStateEvent.text.toString()

        val address = "${addressEvent}, ${city}, ${state}"
        val latLng = getLatLngFromAddress(address)
        val latitude: Double
        val longitude: Double
        if (latLng != null) {
            latitude = latLng.latitude
            longitude = latLng.longitude
        } else {
            Toast.makeText(this@RegisterEventActivity, "Endereço inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val event = EventRegister(
            cep = cep,
            nameEvent = nameEvent,
            description = description,
            category = category,
            addressEvent = addressEvent,
            city = city,
            state = state,
            startData = startDateTime.toString(),
            endData = endDateTime.toString(),
            latitude = latitude.toString(),
            longitude = longitude.toString()
        )

        val service = Api.createService(RetrofitService::class.java)
        val request = service.saveEvent(event)

//        request.enqueue(object : Callback<EventRegister> {
//            @RequiresApi(Build.VERSION_CODES.N)
//            override fun onResponse(call: Call<EventRegister>, response: Response<EventRegister>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@RegisterEventActivity, "Evento cadastrado", Toast.LENGTH_SHORT).show()
//
//                    val createdEvent = response.body()?.event
//                    val eventName = createdEvent?.nameEvent
//
//                    // Aqui você deve fazer uma chamada à API para obter o evento pelo nome
//                    // Supondo que haja um método chamado getEventByName na sua classe de serviço
//
//                    val requestTicket = service.getEvent(eventName)
//                    requestTicket.enqueue(object : Callback<Event> {
//                        override fun onResponse(call: Call<Event>, response: Response<Event>) {
//                            if (response.isSuccessful) {
//                                val event = response.body()
//                                val idEvent = event?.idEvent ?: -1 // Obtém o ID do evento encontrado
//
//                                val intent = Intent(this@RegisterEventActivity, TicketActivity::class.java)
//                                intent.putExtra("idEvent", idEvent)
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                startActivity(intent)
//                            } else {
//                                Toast.makeText(this@RegisterEventActivity, "Erro ao obter o ID do evento", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<Event>, t: Throwable) {
//                            Toast.makeText(this@RegisterEventActivity, "Falha ao obter o ID do evento", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//
//                } else {
//                    Toast.makeText(this@RegisterEventActivity, "Erro no cadastro", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<EventRegister>, t: Throwable) {
//                Toast.makeText(this@RegisterEventActivity, "API não encontrada", Toast.LENGTH_SHORT).show()
//            }
//        })

    }

}