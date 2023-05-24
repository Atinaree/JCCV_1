package com.example.jccv_1.practica3


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.jccv_1.R
import com.example.jccv_1.practica2.SwapperActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Analytics Event

        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integrado")
        analytics.logEvent("InitScreen", bundle)

//setup
        setup()
    }


    private fun setup(){
        title = "Autenticación"
        var registrar = findViewById<Button>(R.id.button3)
        var entrar = findViewById<Button>(R.id.entrarBtn)
        var email = findViewById<EditText>(R.id.textInputLayaout2)
        var contraseña = findViewById<EditText>(R.id.passwordEditText)

        registrar.setOnClickListener(){
            if (email.text.isNotEmpty() && contraseña.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),contraseña.text.toString())
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            gohome(it.result?.user?.email ?:"")
                        }else{
                            alert()
                        }
                    }
            } else {
                alert()
            }
        }

        entrar.setOnClickListener(){
Log.d("123", "hola")

                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)


        }
    }

    private fun alert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
        builder.setPositiveButton("aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun gohome(email: String){

        val homeIntent = Intent(this,SwapperActivity::class.java).apply{
            putExtra("email",email)
        }
        startActivity(homeIntent)

    }

}