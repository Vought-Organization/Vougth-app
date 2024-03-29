package com.example.vought.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vought.R
import com.example.vought.databinding.FragmentRegisterPasswordBinding
import com.example.vought.home.HomeActivity

class RegisterPasswordFragment : Fragment(R.layout.fragment_register_password) {

    private lateinit var binding: FragmentRegisterPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterPasswordBinding.bind(view)
        setupViews()

    }

    private fun setupViews() {
        binding.apply {
            toolbarPassword.setNavigationOnClickListener { findNavController().popBackStack() }
            btnEnter.setOnClickListener {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

}