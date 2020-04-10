package com.example.projectmasjidapps.Donation

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.donate_item_confirmation.*

class DonateItemConfirmation : AppCompatActivity(), View.OnClickListener {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_item_confirmation)
        progressBarDonateConfirm.visibility = View.INVISIBLE

        val name = intent.getStringExtra("name")
        val item = intent.getStringExtra("item")
        val address = intent.getStringExtra("address")

        inputNameDiC.text = name
        inputItemDiC.text = item
        inputAddressDiC.text = address

        buttonConfirmDiC.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonConfirmDiC -> updateToDb(inputNameDiC.text.toString(), inputItemDiC.text.toString(), inputAddressDiC.text.toString())
            R.id.buttonCancel -> cancelPressed()
        }
    }

    private fun cancelPressed() {
        val intent = Intent(this, DonationMain::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun updateToDb(name: String, item: String, address: String) {
        progressBarDonateConfirm.visibility = View.VISIBLE

        val donation = hashMapOf(
            "name" to name,
            "item" to item,
            "address" to address
        )

        // Update to db
        db.collection("Donation Item")
            .add(donation)
            .addOnSuccessListener {
                d("bomohit", "item successfully added")
                Toast.makeText(applicationContext, "Terima Kasih", Toast.LENGTH_SHORT).show()
                progressBarDonateConfirm.visibility = View.INVISIBLE
                cancelPressed()
            }
            .addOnFailureListener { e ->
                d("bomohit", "item fail to add : ", e)
                progressBarDonateConfirm.visibility = View.INVISIBLE
            }
    }

}