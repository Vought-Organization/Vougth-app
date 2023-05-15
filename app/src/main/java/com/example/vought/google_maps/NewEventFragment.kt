package com.example.vought.google_maps

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.vought.databinding.FragmentAddEventBinding
import com.example.vought.model.Event
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class NewEventFragment:Fragment(com.example.vought.R.layout.fragment_add_event) {
    private lateinit var binding: FragmentAddEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater)
        return binding.root

        binding.btnSalveEvent.apply {
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
        val event = Event(
            cep = binding.edtCepEvent.text.toString(),
            name_event = binding.edtEventTitle.text.toString(),
            category_event = binding.edtCategoryEvent.text.toString(),
            description = binding.edtDescriptionEvent.text.toString(),
            addressEvent = binding.edtAddressEvent.text.toString(),
            city = binding.edtCityEvent.text.toString(),
            state = binding.edtStateEvent.text.toString(),
        )
        val service = Api.createService(RetrofitService::class.java)
        val request = service.saveEvent(event)

        request.enqueue(object: Callback<Event> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<Event>,
                response: Response<Event>
            ) {
                println(event)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Evento cadastrado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Erro no cadastro", Toast.LENGTH_SHORT).show()
                    // Erro no registro, tratar aqui
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Toast.makeText(context, "API não encontrada", Toast.LENGTH_SHORT).show()
            }
        })

    }

}