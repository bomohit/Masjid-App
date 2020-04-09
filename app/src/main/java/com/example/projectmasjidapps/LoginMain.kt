package com.example.projectmasjidapps

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_main.*

class LoginMain : AppCompatActivity(), View.OnClickListener {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        progressBarLogin.visibility = View.INVISIBLE

        buttonLogin.setOnClickListener(this)
        textRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        progressBarLogin.visibility = View.VISIBLE
        val i = v.id

        when (i) {
            R.id.buttonLogin -> login(inputLoginUsername.text.toString(), inputLoginPassword.text.toString())
            R.id.textRegister -> register()
        }
    }

    private fun register() {
        progressBarLogin.visibility = View.INVISIBLE
        startActivity(Intent(this, RegisterMain::class.java))
    }

    private fun login(username: String, password: String) {

        // Check if form completed
        if (!validate()) {
            progressBarLogin.visibility = View.INVISIBLE
            return
        }

        if(username != "admin") {
            db.collection("User").document(username)
                .get()
                .addOnSuccessListener { result ->
                    if (password == result.getField<String>("password").toString()) {
                        d("bomohit", "valid login")
                        progressBarLogin.visibility = View.INVISIBLE
                        Toast.makeText(
                            applicationContext,
                            "Welcome ${result.getField<String>("full name")}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        d("bomohit", "invalid login")
                        progressBarLogin.visibility = View.INVISIBLE
                        Toast.makeText(applicationContext, "Akaun tidak wujud", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            db.collection("User").document(username)
                .get()
                .addOnSuccessListener { result ->
                    if (password == result.getField<String>("password").toString()) {
                        d("bomohit", "valid login")
                        progressBarLogin.visibility = View.INVISIBLE
                        Toast.makeText(
                            applicationContext,
                            "Welcome ${result.getField<String>("Admin")}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        d("bomohit", "invalid login")
                        progressBarLogin.visibility = View.INVISIBLE
                        Toast.makeText(applicationContext, "Akaun tidak wujud", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    fun validate(): Boolean {
        var valid = true

        val username = inputLoginUsername.text.toString()
        if (username.isEmpty()) {
            inputLoginUsername.error = "Perlu / Required"
            valid = false
        } else {
            inputLoginUsername.error = null
        }

        val password = inputLoginPassword.text.toString()
        if (password.isEmpty()) {
            inputLoginPassword.error = "Perlu / Required"
            valid = false
        } else {
            inputLoginPassword.error = null
        }

        return valid
    }
}