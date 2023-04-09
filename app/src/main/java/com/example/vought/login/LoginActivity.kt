package com.example.vought.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.vought.R
import com.example.vought.databinding.ActivityLoginBinding
import com.example.vought.register.RegisterActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupListeners()
  }

  private fun setupListeners() {
    binding.apply {
      loginTxtRegister.setOnClickListener {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
      }

      loginEditEmail.addTextChangedListener(watcher)
      loginEditPassword.addTextChangedListener(watcher)

      loginBtnEnter.setOnClickListener {
        login()
      }
    }
  }

  private fun login() {
    binding.loginBtnEnter.showProgress(true)

    binding.loginEditEmailInput.error = "Esse e-mail é inválido"
    binding.loginEditPasswordInput.error = "Senha Incorreta"

    Handler(Looper.getMainLooper()).postDelayed({
      binding.loginBtnEnter.showProgress(false)
    }, 2000)
  }

  private val watcher = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      binding.loginBtnEnter.isEnabled = s.toString().isNotEmpty()
    }

    override fun afterTextChanged(s: Editable?) {
    }
  }
}
