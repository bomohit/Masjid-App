package com.example.projectmasjidapps.Donation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.donation_main.*
import kotlinx.android.synthetic.main.money_flow_statement.*

class MoneyFlowStatement : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.money_flow_statement)

        val db = Firebase.firestore

        val statementList = mutableListOf<statement>()

        getTotalDonation()
        getTotalOut()

        // get data from db
        db.collection("Money Flow Statement")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val dName = document.id
                    val dTotal = document.getField<String>("total").toString()

                    statementList.add(
                        statement(
                            dName,
                            dTotal
                        )
                    )

                    // initialize recycler view
                    recyclerViewMoneyFlowStatement.apply {
                        layoutManager = LinearLayoutManager(this@MoneyFlowStatement)
                        adapter =
                            MoneyFlowStatementAdapter(
                                statementList
                            )
                    }
                }
            }
    }

    private fun getTotalDonation() {

        // Det data from db
        db.collection("Donation").document("Total Collected")
            .get()
            .addOnSuccessListener { result ->
                val total = result.getField<String>("total")

                viewMoneyCollected.text = total
            }
    }

    private fun getTotalOut() {

        // Get data from db
        db.collection("Donation").document("Total Out")
            .get()
            .addOnSuccessListener { result ->
                val total = result.getField<String>("total")

                viewMoneyOut.text = total
            }
    }
}