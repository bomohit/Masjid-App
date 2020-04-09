package com.example.projectmasjidapps.Admin.Booking

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.admin_booking_detail.*

class AdminBookingDetail : AppCompatActivity(), View.OnClickListener {

    val db = Firebase.firestore
    var documentID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_booking_detail)

        val date = intent.getStringExtra("date")
        val name = intent.getStringExtra("name")
        val event = intent.getStringExtra("event")
        val tent = intent.getStringExtra("tent")
        val chair = intent.getStringExtra("chair")
        val id = intent.getStringExtra("id")
        documentID = id.toString()

        viewDateBD.text = date
        viewNameBD.text = name
        viewEventBD.text = event
        viewTentBD.text = tent
        viewChairBD.text = chair

        buttonApprove.setOnClickListener(this)
        buttonReject.setOnClickListener(this)



    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonApprove -> approveBooking(viewDateBD.text.toString(), viewNameBD.text.toString(), viewEventBD.text.toString(), viewTentBD.text.toString(), viewChairBD.text.toString(), documentID)

            R.id.buttonReject -> rejectBooking(documentID)


        }
    }

    private fun rejectBooking(id: String) {
        // Delete booking
        db.collection("Booking").document(id)
            .delete()
            .addOnSuccessListener {
                d("bomohit", "Booking deleted")
                whenDone()
            }
            .addOnFailureListener { e ->
                d("bomohit", "delete error : ", e)
            }
    }

    private fun approveBooking(
        date: String,
        name: String,
        event: String,
        tent: String,
        chair: String,
        id: String
    ) {
        val Booking = hashMapOf(
            "date" to date,
            "event" to event,
            "name" to name,
            "tent" to tent,
            "chair" to chair
        )
        // Add to Confirmed Booking database
        db.collection("Confirmed Booking")
            .add(Booking)
            .addOnSuccessListener {
                d("bomohit", "added to db")
                rejectBooking(id)
            }
    }

    fun whenDone() {
        val intent = Intent(this, AdminBookingList::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}