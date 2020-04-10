package com.example.projectmasjidapps

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.register_main.*

class RegisterMain : AppCompatActivity(), View.OnClickListener {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_main)
        progressBarRegister.visibility = View.INVISIBLE

        buttonConfirm.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.buttonConfirm -> createAccount(inputUsername.text.toString(), inputLoginPassword.text.toString(), inputFullName.text.toString(), inputAddressDi.text.toString(), inputPhoneNo.text.toString())
            R.id.buttonCancel -> onBackPressed()
        }
    }

    private fun createAccount(username: String, password: String, fullName: String, address: String, phoneNo: String) {
        progressBarRegister.visibility = View.VISIBLE

        // Check if the form completed or not
        if (!validate()) {
            return
        }

        val user = hashMapOf(
            "username" to username,
            "password" to password,
            "full name" to fullName,
            "address" to address,
            "phone no" to phoneNo
        )

        // Check if the username already used
        db.collection("User").document(username)
            .get()
            .addOnSuccessListener { result ->
                val uid =result.getField<String>("username")

                if (uid == "null") {
                    d("bomohit", "username: valid")
                    db.collection("User").document(username)
                        .set(user)
                        .addOnSuccessListener {
                            d("bomohit", "account created")
                            progressBarRegister.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext, "Akaun berjaya dibina/ Account Created", Toast.LENGTH_SHORT).show()
//                            onBackPressed()
                        }
                        .addOnFailureListener { e ->
                            progressBarRegister.visibility = View.INVISIBLE
                            d("bomohit", "error creating account ", e)
                        }

                } else {
                    d("bomohit", "username: invalid")
                    inputUsername.error = "Telah dipakai / Invalid"
                    progressBarRegister.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "Kata Laluan Telah Dipakai, Username has been used", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener { e ->
                progressBarRegister.visibility = View.INVISIBLE
                d("bomohit", "error checking username ", e)
            }

    }

    private fun validate(): Boolean {
        var valid = true

        val username = inputUsername.text.toString()
        if (username.isEmpty()) {
            inputUsername.error = "Perlu / Required"
            valid = false
        } else {
            inputUsername.error = null
        }
        val password = inputLoginPassword.text.toString()
        if (password.isEmpty()) {
            inputLoginPassword.error = "Perlu / Required"
            valid = false
        } else {
            inputLoginPassword.error = null
        }
        val fullName = inputFullName.text.toString()
        if (fullName.isEmpty()) {
            inputFullName.error = "Perlu / Required"
            valid = false
        } else {
            inputFullName.error = null
        }
        val address = inputAddressDi.text.toString()
        if (address.isEmpty()) {
            inputAddressDi.error = "Perlu / Required"
            valid = false
        } else {
            inputAddressDi.error = null
        }
        val phoneNo = inputPhoneNo.text.toString()
        if (phoneNo.isEmpty()) {
            inputPhoneNo.error = "Perlu / Required"
            valid = false
        } else {
            inputPhoneNo.error = null
        }

        progressBarRegister.visibility = View.INVISIBLE
        return valid
    }
}