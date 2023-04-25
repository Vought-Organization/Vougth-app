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
import com.example.vought.model.LoginRequest
import com.example.vought.model.UserData
import com.example.vought.register.RegisterActivity
import com.example.vought.register.RegisterWelcomeFragment
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val TIMEOUT_MS = 5000L // 5 segundos
    private val handler = Handler(Looper.getMainLooper())

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

            loginEditEmail.doAfterTextChanged { email ->
                val isValidEmail =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
                loginEditEmailInput.error = if (isValidEmail) null else "E-mail inválido"
            }

            loginEditPassword.doAfterTextChanged { password ->
                loginBtnEnter.isEnabled = password.toString().isNotEmpty()
            }

            val service = Api.getApiUsers().create(RetrofitService::class.java)
            val request = service.getAllUsers()

            loginBtnEnter.setOnClickListener {
                request.enqueue(object: Callback<List<UserData>> {
                    override fun onResponse(
                        call: Call<List<UserData>>,
                        response: Response<List<UserData>>
                    ) {
                        if(response.isSuccessful) {
                            val usuarios = response.body()

                            val edtEmail = binding.loginEditEmail.text.toString()
                            val edtPassword = binding.loginEditPassword.text.toString()

                            val usersMatch = usuarios!!.stream()!!
                                .filter { it.email.equals(edtEmail) && it.password.equals(edtPassword) }
                                .collect(Collectors.toList())

                            if(!usersMatch.isEmpty()){
                                // acitivity
                                return
                            }
                            Toast.makeText(applicationContext, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                        Toast.makeText(applicationContext, "API não encontrada", Toast.LENGTH_SHORT).show()

                    }

                })
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
