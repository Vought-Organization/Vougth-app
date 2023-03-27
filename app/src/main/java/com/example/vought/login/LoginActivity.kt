package com.example.vought.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.example.vought.R
import com.example.vought.register.RegisterActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val btnTextRegister = findViewById<TextView>(R.id.login_txt_register)
    val editTextEmail = findViewById<TextInputEditText>(R.id.login_edit_email)
    val editTextPassword = findViewById<TextInputEditText>(R.id.login_edit_password)

    editTextEmail.addTextChangedListener(watcher)
    editTextPassword.addTextChangedListener(watcher)

    btnTextRegister.setOnClickListener {
      var voltar = Intent (this, RegisterActivity::class.java)
      finish()
      startActivity(voltar)
    }

    val buttonEnter = findViewById<LoadingButton>(R.id.login_btn_enter)
    buttonEnter.setOnClickListener {
      buttonEnter.showProgress(true)

      findViewById<TextInputLayout>(R.id.login_edit_email_input)
        .error = "Esse e-mail é inválido"

      findViewById<TextInputLayout>(R.id.login_edit_password_input)
        .error = "Senha Incorreta"

      Handler(Looper.getMainLooper()).postDelayed({
        buttonEnter.showProgress(false)
      }, 2000)
    }
  }

  private val watcher = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      findViewById<LoadingButton>(R.id.login_btn_enter).isEnabled = s.toString().isNotEmpty()
    }

    override fun afterTextChanged(s: Editable?) {
    }

  }

}