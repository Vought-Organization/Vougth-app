package com.example.vought.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vought.R
import com.example.vought.databinding.FragmentProfileBinding
import com.example.vought.model.UserData
import com.example.vought.model.UserDataUpdate
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import kotlinx.android.synthetic.main.fragment_profile.profile_edit_cpf
import kotlinx.android.synthetic.main.fragment_profile.profile_edit_email
import kotlinx.android.synthetic.main.fragment_profile.profile_edit_name
import kotlinx.android.synthetic.main.fragment_profile.profile_edit_password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val service = Api.createService(RetrofitService::class.java)

        val cpfEditText = binding.profileEditCpf
        val emailEditTex = binding.profileEditEmail
        val passwordEditText = binding.profileEditPassword




        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun updateUser(userId: String, updatedUser: UserData) {


        val userDataToUpdate = UserDataUpdate(
            userName = profile_edit_name.text.toString(),
            cpf = profile_edit_cpf.text.toString(),
            email = profile_edit_email.text.toString(),
            password = profile_edit_password.text.toString()
            )


        val apiService = Api.createService(RetrofitService::class.java)

        val call = apiService.saveUser(updatedUser)
        call.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    // Atualização do usuário bem-sucedida
                    val updatedUserData = response.body()
                    // Faça algo com os dados do usuário atualizado, se necessário
                } else {
                    // A solicitação falhou
                    // Lide com a resposta de erro, se necessário
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                // Ocorreu um erro durante a solicitação
                // Lide com o erro, se necessário
            }
        })
    }




}