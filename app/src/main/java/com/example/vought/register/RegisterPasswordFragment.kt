package com.example.vought.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vought.R
import com.example.vought.databinding.FragmentRegisterBinding
import com.example.vought.databinding.FragmentRegisterPasswordBinding
import com.example.vought.databinding.FragmentRegisterWelcomeBinding
import com.example.vought.login.LoginActivity

class RegisterPasswordFragment : Fragment(R.layout.fragment_register_password) {
  private lateinit var binding: FragmentRegisterPasswordBinding
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentRegisterPasswordBinding.bind(view)

    binding.apply {
      loginBtnEnter.setOnClickListener {
        findNavController().navigate(R.id.action_fragment_register_password_vought_to_fragmen_welcome)
      }
    }
  }
}