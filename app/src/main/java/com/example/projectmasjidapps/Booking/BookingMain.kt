package com.example.projectmasjidapps.Booking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import kotlinx.android.synthetic.main.booking_main.*
import java.util.*

class BookingMain : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_main)

        buttonNext.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)

        // Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        inputDateB.inputType = InputType.TYPE_NULL

        inputDateB.setOnClickListener {
            d("bomohit", "date clicked")
            val date = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                inputDateB.setText("$mDay-${mMonth+1}-$mYear")
            }, year, month, day)

            date.show()
        }

    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonNext -> passToConfirmation(inputNameB.text.toString(), inputDateB.text.toString(), inputEventB.text.toString(), inputTentB.text.toString(), inputChairB.text.toString())
            R.id.buttonCancel -> onBackPressed()
        }
    }

    private fun passToConfirmation(
        name: String,
        date: String,
        event: String,
        tent: String,
        chair: String
    ) {
        // Check if the form completed or not

        if (!validate()) {
            return
        }

        val intent = Intent(this, BookingConfirmation::class.java)
        intent.putExtra("name", name)
        intent.putExtra("date", date)
        intent.putExtra("event", event)
        intent.putExtra("tent", tent)
        intent.putExtra("chair", chair)
        startActivity(intent)
    }

    fun validate(): Boolean {
        var valid = true

        val name = inputNameB.text.toString()
        if (name.isEmpty()) {
            inputNameB.error = "Perlu / Required"
            valid = false
        } else {
            inputNameB.error = null
        }

        val date = inputDateB.text.toString()
        if (date.isEmpty()) {
            inputDateB.error = "Perlu / Required"
            valid = false
        } else {
            inputDateB.error = null
        }

        val event = inputEventB.text.toString()
        if (event.isEmpty()) {
            inputEventB.error = "Perlu / Required"
            valid = false
        } else {
            inputEventB.error = null
        }

        val tent = inputTentB.text.toString()
        if (tent.isEmpty()) {
            inputTentB.error = "Perlu / Required"
            valid = false
        } else {
            inputTentB.error = null
        }

        val chair = inputChairB.text.toString()
        if (chair.isEmpty()) {
            inputChairB.error = "Perlu / Required"
            valid = false
        } else {
            inputChairB.error = null
        }

        return valid
    }
}