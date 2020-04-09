package com.example.projectmasjidapps.Admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.Admin.Booking.AdminBookingList
import com.example.projectmasjidapps.Admin.Tazkirah.AdminListTazkirah
import com.example.projectmasjidapps.R
import kotlinx.android.synthetic.main.admin_home.*

class AdminHome : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_home)

        buttonConfirmTazkirah.setOnClickListener(this)
        buttonConfirmBooking.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonConfirmTazkirah -> startActivity(Intent(this, AdminListTazkirah::class.java))

            R.id.buttonConfirmBooking -> startActivity(Intent(this, AdminBookingList::class.java))
        }
    }
}