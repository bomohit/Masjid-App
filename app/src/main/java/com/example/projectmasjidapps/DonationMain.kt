package com.example.projectmasjidapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.donation_main.*

class DonationMain : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donation_main)

        // Display total donation collected
        getTotalDonation()

        // Display total out / money flow statement
        getTotalOut()


    }

    fun getTotalDonation() {

        // Det data from db
        db.collection("Donation").document("Total Collected")
            .get()
            .addOnSuccessListener { result ->
                val total = result.getField<String>("total")

                viewTotalCollected.text = total
            }
    }

    fun getTotalOut() {

        // Get data from db
        db.collection("Donation").document("Total Out")
            .get()
            .addOnSuccessListener { result ->
                val total = result.getField<String>("total")

                viewTotalOut.text = total
            }
    }
}