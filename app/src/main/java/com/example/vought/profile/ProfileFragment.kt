package com.example.vought.profile

import android.content.Context
import android.content.SharedPreferences
import android.nfc.tech.MifareClassic
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vought.databinding.FragmentProfileBinding
import com.example.vought.model.UserData
import com.example.vought.model.UserDataUpdate
import com.example.vought.rest.Api
import com.example.vought.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var isEditingEnabled = false
    private var userId: String? = null

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userId", null)

        setEditTextEnabled(false)

        val service = Api.createService(RetrofitService::class.java)
        val getUserRequest = service.getUserById(userId ?: "")

        getUserRequest.enqueue(object : Callback<UserDataUpdate> {
            override fun onResponse(call: Call<UserDataUpdate>, response: Response<UserDataUpdate>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        bindUserData(user)
                        println(user)
                    }
                }
            }

            override fun onFailure(call: Call<UserDataUpdate>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Falha ao obter dados do usuário. Tente novamente!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.editButton.setOnClickListener {
            isEditingEnabled = !isEditingEnabled
            setEditTextEnabled(isEditingEnabled)

            if (isEditingEnabled) {
                binding.editButton.text = "Salvar"

                binding.layoutNameUser.visibility = View.INVISIBLE
                binding.profileEditName.visibility = View.VISIBLE

                binding.profileEditPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD


            } else {
                binding.profileEditPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD

                binding.layoutNameUser.visibility = View.VISIBLE
                binding.profileEditName.visibility = View.INVISIBLE

                binding.editButton.text = "Editar"
                val updatedUser = createUpdatedUser()

                updateUser(userId, updatedUser)

                bindUserData(updatedUser)
            }
        }


        return binding.root
    }

    private fun bindUserData(user: UserDataUpdate) {
        with(binding) {
            profileEditEmail.setText(user.email)
            profileViewName.text = user.userName
            profileEditName.setText(user.userName)
            profileEditPassword.setText(user.password)
            profileEditCpf.setText(user.cpf)
            profileEditCep.setText(user.cep)
        }
    }



    private fun setEditTextEnabled(enabled: Boolean) {
        with(binding) {
            profileEditEmail.isEnabled = enabled
            profileEditName.isEnabled = enabled
            profileEditPassword.isEnabled = enabled
            profileEditCpf.isEnabled = enabled
            profileEditCep.isEnabled = enabled
        }
    }

    private fun createUpdatedUser(): UserDataUpdate {
        return UserDataUpdate(
            idUser = userId ?: "",
            userName = binding.profileEditName.text.toString(),
            cpf = binding.profileEditCpf.text.toString(),
            email = binding.profileEditEmail.text.toString(),
            password = binding.profileEditPassword.text.toString(),
            cep = binding.profileEditCep.text.toString()
        )
    }

    private fun updateUser(id: String?, updatedUser: UserDataUpdate) {
        val apiService = Api.createService(RetrofitService::class.java)
        apiService.updateUserById(id ?: "", updatedUser)
            .enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Perfil atualizado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Falha ao atualizar perfil. Tente novamente!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Falha ao atualizar perfil. Tente novamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
