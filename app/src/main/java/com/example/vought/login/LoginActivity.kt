package com.example.vought.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vought.R
import com.example.vought.databinding.ActivityLoginBinding
import com.example.vought.register.RegisterActivity
import com.example.vought.register.RegisterWelcomeFragment
import com.example.vought.rest.RetrofitService
import com.example.vought.viewmodel.LoginViewModel
import com.example.vought.viewmodel.LoginViewModelFactory
import com.example.vought.repositories.UserRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val retrofitService = RetrofitService.Instance()
    private val TIMEOUT_MS = 5000L // 5 segundos
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(UserRepository(retrofitService))
        )[LoginViewModel::class.java]

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            loginTxtRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            loginEditEmail.doAfterTextChanged { email ->
                val isValidEmail =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
                loginEditEmailInput.error = if (isValidEmail) null else "E-mail inválido"
            }

            loginEditPassword.doAfterTextChanged { password ->
                loginBtnEnter.isEnabled = password.toString().isNotEmpty()
            }

            loginBtnEnter.setOnClickListener {
                val email = loginEditEmail.text.toString()
                val password = loginEditPassword.text.toString()

                if (loginEditEmailInput.error != null) {
                    return@setOnClickListener
                }

                viewModel.usersLiveData.observe(this@LoginActivity, Observer { users ->
                    binding.loginBtnEnter.showProgress(false)
                    if (users.isNullOrEmpty()){
                        val intent = Intent(this@LoginActivity, RegisterWelcomeFragment::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        binding.loginBtnEnter.isEnabled = true
                        binding.loginEditPassword.error = getString(R.string.invalid_email)
                    }
                })
                viewModel.errorMessage.observe(this@LoginActivity, Observer {
                    binding.loginBtnEnter.showProgress(false) })
            }
        }
    }

    private fun startTimeout() {
        handler.postDelayed({
            binding.loginBtnEnter.showProgress(false)
            binding.loginBtnEnter.isEnabled = true
            binding.loginEditEmail.error = getString(R.string.invalid_email)
            binding.loginEditPassword.error = getString(R.string.invalid_email)
        }, TIMEOUT_MS)
    }

}
