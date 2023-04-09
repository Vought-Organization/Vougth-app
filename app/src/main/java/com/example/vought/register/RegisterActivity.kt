package com.example.vought.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.vought.R
import com.example.vought.databinding.ActivityLoginBinding
import com.example.vought.databinding.ActivityRegisterBinding
import com.example.vought.login.LoadingButton
import com.example.vought.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRegisterBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

}