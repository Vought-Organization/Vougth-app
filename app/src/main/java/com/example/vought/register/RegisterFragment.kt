package com.example.vought.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vought.R
import com.example.vought.databinding.FragmentRegisterBinding
import com.example.vought.login.LoginActivity
import com.example.vought.repositories.UserRepository
import com.example.vought.rest.RetrofitService


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        setupListeners()


        binding.apply {
            registerBtnEnter.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_register_vought_to_fragment_register_password_vought)
            }
        }
    }



    private fun setupListeners() {
        binding.apply {
            registerTxtLogin.setOnClickListener {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }

        }
    }


}
