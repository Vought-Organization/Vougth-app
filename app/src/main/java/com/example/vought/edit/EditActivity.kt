package com.example.vought.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.vought.R
import com.example.vought.rest.RetrofitService
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.view.View
import android.widget.*
import com.example.vought.home.HomeActivity
import com.example.vought.login.LoadingButton
import com.example.vought.model.Address
import com.example.vought.model.Event
import com.example.vought.model.EventDataUpdate
import com.example.vought.rest.Api
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_myevents.idEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class EditActivity : AppCompatActivity() {
    private lateinit var edtEventTitle: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var edtDescriptionEvent: EditText
    private lateinit var edtCepEvent: EditText
    private lateinit var btnCep: Button
    private lateinit var edtAddressEvent: EditText
    private lateinit var edtStateEvent: EditText
    private lateinit var edtCityEvent: EditText
    private lateinit var inDate: EditText
    private lateinit var btnDate: Button
    private lateinit var inTime: EditText
    private lateinit var btnTime: Button
    private lateinit var inTimeDuration: EditText
    private lateinit var btnSaveEvent: LoadingButton
    private var eventId: Int = 0
    private lateinit var txtDate: EditText
    private lateinit var txtTime: EditText
    private lateinit var viaCepService: RetrofitService
    private val categories = arrayOf(
        "Selecione uma categoria", "show", "palestra", "teatro", "passeio", "congresso", "infantil", "standup"
    )
    private lateinit var selectedCategory: String
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        viaCepService = Api.createViaCepService()

        eventId = intent.getIntExtra("eventId", 0)

        edtEventTitle = findViewById(R.id.edt_event_title)
        spinnerCategory = findViewById(R.id.spinner_category_event)
        edtDescriptionEvent = findViewById(R.id.edt_description_event)
        edtCepEvent = findViewById(R.id.edt_cep_event)
        btnCep = findViewById(R.id.btn_cep)
        edtAddressEvent = findViewById(R.id.edt_address_event)
        edtStateEvent = findViewById(R.id.edt_state_event)
        edtCityEvent = findViewById(R.id.edt_city_event)
        inDate = findViewById(R.id.in_date)
        btnDate = findViewById(R.id.btn_date)
        inTime = findViewById(R.id.in_time)
        btnTime = findViewById(R.id.btn_time)
        inTimeDuration = findViewById(R.id.in_time_duration)
        btnSaveEvent = findViewById(R.id.btn_salve_event)

        btnTime.setOnClickListener(this::onClickTime)

        btnCep.setOnClickListener {
            searchCep()
        }

        btnDate.setOnClickListener {
            onClickDate()
        }

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

        btnSaveEvent.setOnClickListener {
            saveEventChanges()
        }

        val service = Api.createService(RetrofitService::class.java)
        val request = service.getEventById(eventId)

        request.enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    val event = response.body()
                    if (event != null) {
                        edtEventTitle.setText(event.nameEvent)
                        edtDescriptionEvent.setText(event.description)
                        edtCepEvent.setText(event.cep)
                        edtAddressEvent.setText(event.addressEvent)
                        edtStateEvent.setText(event.state)
                        edtCityEvent.setText(event.city)
                        val position = categories.indexOf(event.category)
                        spinnerCategory.setSelection(position)
                        val data = event.startData.toString()
                        inDate.setText(data.substring(0, 10))
                        inTime.setText(data.substring(11, 16))
                        inTimeDuration.setText(data.substring(11, 12))
                    }
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getLatLngFromAddress(address: String): LatLng? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addressList = geocoder.getFromLocationName(address, 1)
        if (addressList != null) {
            if (addressList.isNotEmpty()) {
                val location = addressList?.get(0)
                if (location != null) {
                    val latitude = addressList[0].latitude
                    val longitude = addressList[0].longitude
                    return LatLng(latitude, longitude)
                }
            }
        }
        return null
    }

    private fun searchCep() {
        val cep = edtCepEvent.text.toString()
        if (cep.isNotEmpty()) {
            val call = viaCepService.getAddress(cep)
            call.enqueue(object : Callback<Address> {
                override fun onResponse(call: Call<Address>, response: Response<Address>) {
                    if (response.isSuccessful) {
                        val address = response.body()
                        if (address != null) {
                            val addressString = "${address.logradouro}, ${address.localidade}, ${address.uf}"
                            val latLng = getLatLngFromAddress(addressString)
                            if (latLng != null) {
                                edtAddressEvent.setText(address.logradouro)
                                edtStateEvent.setText(address.uf)
                                edtCityEvent.setText(address.localidade)
                            }
                        }
                    } else {
                        Toast.makeText(this@EditActivity, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Address>, t: Throwable) {
                    Toast.makeText(this@EditActivity, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Informe um CEP válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickTime(v: View) {
        if (v == btnDate) {
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    inDate.setText("$year-${monthOfYear + 1}-$dayOfMonth")
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        }
        if (v == btnTime) {
            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMinute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
                    inTime.setText("$minute:$hourOfDay")
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }
    }

    private fun onClickDate() {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                inDate.setText("$year-${monthOfYear + 1}-$dayOfMonth")
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun saveEventChanges() {
        val eventTitle = edtEventTitle.text.toString().trim()
        val eventDescription = edtDescriptionEvent.text.toString().trim()
        val eventCep = edtCepEvent.text.toString().trim()
        val eventAddress = edtAddressEvent.text.toString().trim()
        val eventState = edtStateEvent.text.toString().trim()
        val eventCity = edtCityEvent.text.toString().trim()
        val address = "$eventAddress, $eventCity, $eventState"
        val latLng = getLatLngFromAddress(address)
        val latitude: Double
        val longitude: Double
        if (latLng != null) {
            latitude = latLng.latitude
            longitude = latLng.longitude
        } else {
            Toast.makeText(this@EditActivity, "Invalid address", Toast.LENGTH_SHORT).show()
            return
        }

        val date = inDate.text.toString()
        val time = inTime.text.toString()

        val startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-M-d"))
        val startTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

        val startDateTime = LocalDateTime.of(startDate, startTime)
        val timeDuration: EditText = inTimeDuration
        val duration: Double = timeDuration.text.toString().toDouble()
        val endDateTime = startDateTime.plusHours(duration.toInt().toLong())

        val event = EventDataUpdate(
            eventId,
            eventCep,
            eventTitle,
            selectedCategory,
            eventDescription,
            latitude.toString(),
            longitude.toString(),
            eventAddress,
            eventCity,
            eventState,
            startDateTime.toString(),
            endDateTime.toString()
        )
        val service = Api.createService(RetrofitService::class.java)
        val request = service.updateEventById(event.idEvent, event)

        request.enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditActivity, "Evento editado com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@EditActivity, "Failed to save event changes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Toast.makeText(this@EditActivity, "Failed to save event changes", Toast.LENGTH_SHORT).show()
            }
        })
    }
}