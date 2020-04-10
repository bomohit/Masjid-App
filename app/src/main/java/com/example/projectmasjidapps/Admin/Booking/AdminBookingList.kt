package com.example.projectmasjidapps.Admin.Booking

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.admin_booking_list.*

class AdminBookingList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_booking_list)
        progressBarBookingList.visibility = View.VISIBLE

        val db = Firebase.firestore
        val booking = mutableListOf<BookingList>()


        db.collection("Booking")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val id = document.id
                    val name = document.getField<String>("name").toString()
                    val event = document.getField<String>("event").toString()
                    val date = document.getField<String>("date").toString()
                    val tent = document.getField<String>("tent").toString()
                    val chair = document.getField<String>("chair").toString()

                    booking.add(BookingList(name,date,event,tent,chair,id))

                    recyclerViewBooking.apply {
                        layoutManager = LinearLayoutManager(this@AdminBookingList)
                        adapter = BookingListAdapter(booking)
                    }
                }
                progressBarBookingList.visibility = View.INVISIBLE
            }
            .addOnFailureListener { e ->
                d("bomohit", "fail : ",e)
                progressBarBookingList.visibility = View.INVISIBLE
            }


    }
}