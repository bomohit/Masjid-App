package com.example.projectmasjidapps

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.Booking.BookingMain
import com.example.projectmasjidapps.Donation.DonationMain
import com.example.projectmasjidapps.Tazkirah.TazkirahMain
import kotlinx.android.synthetic.main.home_main.*

class HomeMain : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        buttonDonation.setOnClickListener(this)
        buttonBooking.setOnClickListener(this)
        buttonTazkirah.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonDonation -> startActivity(Intent(this, DonationMain::class.java))
            R.id.buttonBooking -> startActivity(Intent(this, BookingMain::class.java))
            R.id.buttonTazkirah -> startActivity(Intent(this, TazkirahMain::class.java))
        }
    }
}