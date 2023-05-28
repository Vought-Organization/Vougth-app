package com.example.vought.eventos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.vought.R
import com.example.vought.databinding.ActivityRegisterEventBinding
import com.example.vought.model.Event
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class RegisterEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_event)

        binding = ActivityRegisterEventBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSalveEvent.setOnClickListener {
            registerEvent()
        }

        val autoD8 = binding.autoD8
        val autoTime =  binding.autoTime

        val dateF = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
        val timeF = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date: String = dateF.format(Calendar.getInstance().getTime())
        val time: String = timeF.format(Calendar.getInstance().getTime())

        autoD8.setText(time)
        autoTime.setText(time)
    }

    private fun registerEvent() {
//        val event = Event(
//            cep = binding.edtCepEvent.text.toString(),
//            name_event = binding.edtEventTitle.text.toString(),
//            category_event = binding.edtCategoryEvent.text.toString(),
//            description = binding.edtDescriptionEvent.text.toString(),
//            addressEvent = binding.edtAddressEvent.text.toString(),
//            city = binding.edtCityEvent.text.toString(),
//            state = binding.edtStateEvent.text.toString(),
//
//        )
//        val service = Api.createService(RetrofitService::class.java)
//        val request = service.saveEvent(event)
//
//        request.enqueue(object: Callback<Event> {
//            @RequiresApi(Build.VERSION_CODES.N)
//            override fun onResponse(
//                call: Call<Event>,
//                response: Response<Event>
//            ) {
//                println(event)
//                if (response.isSuccessful) {
//                    Toast.makeText(this@RegisterEventActivity, "Evento cadastrado", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@RegisterEventActivity, "Erro no cadastro", Toast.LENGTH_SHORT).show()
//                    // Erro no registro, tratar aqui
//                }
//            }
//
//            override fun onFailure(call: Call<Event>, t: Throwable) {
//                Toast.makeText(this@RegisterEventActivity, "API não encontrada", Toast.LENGTH_SHORT).show()
//            }
//        })
    }
}
