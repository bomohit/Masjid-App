package com.example.projectmasjidapps.Booking

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.booking_confirmation.*

class BookingConfirmation : AppCompatActivity(), View.OnClickListener {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_confirmation)

        // get the data passed
        val name = intent.getStringExtra("name")
        val date = intent.getStringExtra("date")
        val event = intent.getStringExtra("event")
        val tent = intent.getStringExtra("tent")
        val chair = intent.getStringExtra("chair")

        viewNameBc.text = name
        viewDateBc.text = date
        viewEventBc.text = event
        viewTentBc.text = tent
        viewChairBc.text = chair

        buttonConfirm.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonConfirm -> confirmBooking(
                viewNameBc.text.toString(),
                viewDateBc.text.toString(),
                viewEventBc.text.toString(),
                viewTentBc.text.toString(),
                viewChairBc.text.toString()
            )
            R.id.buttonCancel -> cancelBookin()
        }
    }

    private fun cancelBookin() {
        val intent = Intent(this, BookingMain::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
        finish()
    }

    private fun confirmBooking(
        name: String,
        date: String,
        event: String,
        tent: String,
        chair: String
    ) {
        val booking = hashMapOf(
            "name" to name,
            "date" to date,
            "event" to event,
            "tent" to tent,
            "chair" to chair
        )

        db.collection("Booking")
            .add(booking)
            .addOnSuccessListener {
                d("bomohit", "booking recorded")
                Toast.makeText(applicationContext, "Tempahan dibuat", Toast.LENGTH_SHORT).show()
                cancelBookin()
            }
            .addOnFailureListener { e ->
                d("bomohit", "booking fail : ", e)
            }
    }
}