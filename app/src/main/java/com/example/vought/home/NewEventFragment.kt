package com.example.vought.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vought.R
import com.example.vought.databinding.FragmentAddEventBinding
import com.example.vought.eventos.RegisterEventActivity

class NewEventFragment : Fragment(R.layout.fragment_add_event) {
    private lateinit var binding: FragmentAddEventBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.CardCima.setOnClickListener {
            val intent = Intent(requireContext(), RegisterEventActivity::class.java)
            startActivity(intent)

        }
    }
}