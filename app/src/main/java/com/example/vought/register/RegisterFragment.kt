package com.example.vought.register

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vought.R
import com.example.vought.databinding.FragmentRegisterBinding
import com.example.vought.login.LoginActivity
import com.example.vought.model.UserData
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        setupListeners()

        binding.apply {
            registerBtnEnter.setOnClickListener {
                register()
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

    private fun register() {

        val userData = UserData(
            userName = binding.editName.text.toString(),
            email = binding.registerEditEmail.text.toString(),
            cpf = binding.loginEditCpf.text.toString(),
            password = binding.editPassword.text.toString(),
            cep = binding.registerEditCep.text.toString(),
            idUser = null
        )

        val service = Api.createService(RetrofitService::class.java)
        val request = service.saveUser(userData)

        request.enqueue(object: Callback<UserData> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                println(userData)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Registro bem sucedido", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_fragment_register_vought_to_fragment_register_password_vought)
                } else {
                    Toast.makeText(context, "Erro no registro", Toast.LENGTH_SHORT).show()
                    // Erro no registro, tratar aqui
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Toast.makeText(context, "API não encontrada", Toast.LENGTH_SHORT).show()
            }
        })


    }


}