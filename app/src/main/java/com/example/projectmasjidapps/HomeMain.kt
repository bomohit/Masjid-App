package com.example.projectmasjidapps

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.home_main.*

class HomeMain : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        buttonDonation.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonDonation -> startActivity(Intent(this, DonationMain::class.java))
        }
    }
}