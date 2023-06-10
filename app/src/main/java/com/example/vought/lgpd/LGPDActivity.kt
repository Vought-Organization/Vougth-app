package com.example.vought.lgpd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.example.vought.R
import com.example.vought.login.LoginActivity

class LGPDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_lgpdactivity)

        val btnCadastroGoogle = findViewById<Button>(R.id.btn_cadastro_google)

        btnCadastroGoogle.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}