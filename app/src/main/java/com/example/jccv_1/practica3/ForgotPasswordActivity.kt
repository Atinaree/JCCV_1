package com.example.jccv_1.practica3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.jccv_1.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        //Analytics Event

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integrado")
        analytics.logEvent("InitScreen", bundle)

//setup
        setup()
    }


    private fun setup() {
        title = "Autenticación"
        var registrar = findViewById<Button>(R.id.button3)
        var email = findViewById<EditText>(R.id.textInputLayaout2)

        registrar.setOnClickListener() {
            val email: String = email.text.toString().trim() { it <= ' ' }
            if (email.isEmpty()) {
                alert()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Revise su correo electrónico", Toast.LENGTH_LONG)
                                .show()
                            finish()
                        }else{
                            alert()
                        }
                    }
            }
        }
    }

    private fun alert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El usuario no existe.")
        builder.setPositiveButton("aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}