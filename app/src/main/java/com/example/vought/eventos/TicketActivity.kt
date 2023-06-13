package com.example.vought.eventos

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.vought.R
import com.example.vought.databinding.ActivityTicketBinding
import com.example.vought.home.HomeActivity
import com.example.vought.model.Event
import com.example.vought.model.EventRegister
import com.example.vought.model.Ticket
import com.example.vought.model.TicketRegisterData
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalveEvent.setOnClickListener {
            registerTicket()
        }
    }

    private fun registerTicket() {
        val valorTicket: Double = binding.edtValue.text.toString().toDouble()

        val idEventIntent = intent.getIntExtra("eventId", 0)

        val evento = TicketRegisterData.Evento(idEventIntent)

        val ticket = TicketRegisterData(valorTicket, evento)

        val service = Api.createService(RetrofitService::class.java)

        val request = service.postTicket(ticket)

        request.enqueue(object : Callback<TicketRegisterData> {
            override fun onResponse(
                call: Call<TicketRegisterData>,
                response: Response<TicketRegisterData>
            ) {
                Toast.makeText(this@TicketActivity, "Ingresso cadastrado com sucesso! $idEventIntent", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@TicketActivity, HomeActivity::class.java)
                startActivity(intent)

            }

            override fun onFailure(call: Call<TicketRegisterData>, t: Throwable) {
                Toast.makeText(this@TicketActivity, "Deu Errado", Toast.LENGTH_SHORT).show()
            }

        })

    }
}