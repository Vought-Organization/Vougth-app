package com.example.vought.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.vought.databinding.ActivityLoginBinding
import com.example.vought.home.HomeActivity
import com.example.vought.model.UserData
import com.example.vought.register.RegisterActivity
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val email = binding.loginEditEmail.text.toString()
        val password = binding.loginEditPassword.text.toString()

        val service = Api.createService(RetrofitService::class.java)
        val request = service.getAllUsers()

        request.enqueue(object: Callback<List<UserData>> {
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()

                    if (!users.isNullOrEmpty()) {
                        val user = users.find { it.email == email && it.password == password }

                        if (user != null) {
                            // Armazena o ID em cache
                            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putString("userId", user.idUser).apply()

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


}
