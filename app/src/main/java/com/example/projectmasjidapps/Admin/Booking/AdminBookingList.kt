package com.example.projectmasjidapps.Admin.Booking

import android.os.Bundle
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

        val db = Firebase.firestore
        val booking = mutableListOf<BookingList>()

//        for (i in 0..5) {
//            booking.add(BookingList("Faris", "20-1-2020", "Bomoh iT", "5", "10", "2525"))
//        }

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
            }


    }
}