package com.example.projectmasjidapps.Management

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.mayyit_management.*

class MayyitManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mayyit_management)

        val db = Firebase.firestore

        db.collection("Management").document("Jenazah")
            .get()
            .addOnSuccessListener { result ->
                val name = result.getField<String>("name")
                val phoneNo = result.getField<String>("phone no")

                viewNameM.text = name
                viewPhoneM.text = phoneNo
            }

    }
}