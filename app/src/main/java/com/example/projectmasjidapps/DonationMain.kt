package com.example.projectmasjidapps

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.donation_main.*

class DonationMain : AppCompatActivity(), View.OnClickListener {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donation_main)

        // Display total donation collected
        getTotalDonation()

        // Display total out / money flow statement
        getTotalOut()

        buttonItemDonation.setOnClickListener(this)
        buttonMoneyFlow.setOnClickListener(this)


    }

    private fun getTotalDonation() {

        // Det data from db
        db.collection("Donation").document("Total Collected")
            .get()
            .addOnSuccessListener { result ->
                val total = result.getField<String>("total")

                viewTotalCollected.text = total
            }
    }

    private fun getTotalOut() {

        // Get data from db
        db.collection("Donation").document("Total Out")
            .get()
            .addOnSuccessListener { result ->
                val total = result.getField<String>("total")

                viewTotalOut.text = total
            }
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonMoneyFlow -> openStatement()
            R.id.buttonItemDonation -> startActivity(Intent(this, DonateItem::class.java))
        }
    }

    private fun openStatement() {
        startActivity(Intent(this, MoneyFlowStatement::class.java))
    }
}