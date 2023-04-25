package com.example.vought.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vought.R
import com.example.vought.databinding.FragmentRegisterPasswordBinding
import com.example.vought.databinding.FragmentRegisterWelcomeBinding
import com.example.vought.login.LoginActivity

class RegisterWelcomeFragment : Fragment() {

  private lateinit var binding: FragmentRegisterWelcomeBinding
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register_welcome, container, false)
    binding.btnWelcome.setOnClickListener {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }
  }

}