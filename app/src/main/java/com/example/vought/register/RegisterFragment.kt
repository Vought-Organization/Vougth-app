package com.example.vought.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vought.R
import com.example.vought.databinding.FragmentRegisterBinding
import com.example.vought.login.LoginActivity
import com.example.vought.model.UserData
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import okhttp3.ResponseBody
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
                findNavController().navigate(R.id.action_fragment_register_vought_to_fragment_register_password_vought)
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
        register()
    }
    private fun register() {
        val service = Api.createService(RetrofitService::class.java)

        val name = binding.editName.text.toString()
        val email = binding.registerEditEmail.text.toString()
        val cpf = binding.loginEditCpf.text.toString()
        val password = binding.editPassword.text.toString()
        val cep = binding.registerEditCep.text.toString()

        val request = UserData(name, email, cpf, password, cep)

        val call = service.saveUser(request)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Registro bem sucedido", Toast.LENGTH_SHORT).show()
                    // Registro bem-sucedido, fazer algo aqui
                } else {
                    Toast.makeText(context, "Erro no registro", Toast.LENGTH_SHORT).show()
                    // Erro no registro, tratar aqui
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "API não encontrada", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
