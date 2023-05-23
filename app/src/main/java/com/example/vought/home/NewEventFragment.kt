package com.example.vought.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.vought.R
import com.example.vought.databinding.FragmentAddEventBinding
import com.example.vought.databinding.FragmentHomeBinding
import com.example.vought.eventos.RegisterEventActivity
import com.example.vought.model.Event
import com.example.vought.register.RegisterActivity
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NewEventFragment : Fragment(R.layout.fragment_add_event) {
    private lateinit var binding: FragmentAddEventBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater)
       return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            cardCima.setOnClickListener {
                val intent = Intent(requireContext(), RegisterEventActivity::class.java)
                startActivity(intent)
            }
        }
    }
}