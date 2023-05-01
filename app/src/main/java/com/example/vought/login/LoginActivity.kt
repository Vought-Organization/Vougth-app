package com.example.vought.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vought.HomeActivity
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

            loginBtnEnter.setOnClickListener {
                request()
            }
        }
    }

    private fun request() {
        val service = Api.createService(RetrofitService::class.java)
        val request = service.getAllUsers()

        val email = binding.loginEditEmail
        val password = binding.loginEditPassword

        request.enqueue(object: Callback<List<UserData>> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                println(response.body())
                if (response.isSuccessful){
                    if (response.body()?.isNotEmpty() == true) {
                        val users = response.body()!!
                        val user = users.find{ it.email == email.text.toString() && it.password == password.text.toString() }
                        if (user != null) {
                            Toast.makeText(applicationContext, "Login bem sucedido", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Não foram encontrados usuários", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                Toast.makeText(applicationContext, "API não encontrada", Toast.LENGTH_SHORT).show()
            }
        })
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
