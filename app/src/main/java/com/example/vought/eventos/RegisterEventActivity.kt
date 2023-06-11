package com.example.vought.eventos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.vought.databinding.ActivityRegisterEventBinding
import com.example.vought.login.LoadingButton
import com.example.vought.model.EventRegister
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalveEvent.setOnClickListener {
            registerEvent()
        }

        binding.btnCep.setOnClickListener {

        }

        btnDatePicker = binding.btnDate
        btnTimePicker = binding.btnTime
        txtDate = binding.inDate
        txtTime = binding.inTime

        btnDatePicker.setOnClickListener {
            onClickDate()
        }
        btnTimePicker.setOnClickListener(this::onClick)

    }
    private fun onClickDate(){
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

    private fun registerEvent() {
        val date = txtDate.text.toString()
        val time = txtTime.text.toString()

        val startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d-M-yyyy"))
        val startTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

        val startDateTime = LocalDateTime.of(startDate, startTime)
        val endDateTime = startDateTime.plusHours(2) // Adiciona uma duração fixa de 2 horas

        val event = EventRegister(
            cep = binding.edtCepEvent.text.toString(),
            nameEvent = binding.edtEventTitle.text.toString(),
            description = binding.edtDescriptionEvent.text.toString(),
            addressEvent = binding.edtAddressEvent.text.toString(),
            city = binding.edtCityEvent.text.toString(),
            state = binding.edtStateEvent.text.toString(),
            startData = startDateTime.toString(), // Altere para a representação em String
            endData = endDateTime.toString() // Altere para a representação em String
        )

        val service = Api.createService(RetrofitService::class.java)
        val request = service.saveEvent(event)

        request.enqueue(object : Callback<EventRegister> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<EventRegister>, response: Response<EventRegister>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterEventActivity, "Evento cadastrado", Toast.LENGTH_SHORT).show()

                    // Trate o evento cadastrado (savedEvent) aqui
                } else {
                    Toast.makeText(this@RegisterEventActivity, "Erro no cadastro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventRegister>, t: Throwable) {
                Toast.makeText(this@RegisterEventActivity, "API não encontrada", Toast.LENGTH_SHORT).show()
            }
        })
    }
}