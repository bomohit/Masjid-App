package com.example.projectmasjidapps

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.request_tazkirah.*
import java.util.*

class RequestTazkirah : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    View.OnClickListener {

    val db = Firebase.firestore
    var waktu = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_tazkirah)

        // Time Spinner
        spinnerTazkirahTime.onItemSelectedListener = this
        buttonConfirm.setOnClickListener(this)
        buttonBack.setOnClickListener(this)

        ArrayAdapter.createFromResource(
            this,
            R.array.waktu,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTazkirahTime.adapter = adapter
        }

        // Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        inputTazkirahDate.setOnClickListener {
            d("bomohit", "date clicked")
            val date = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                inputTazkirahDate.text = "$mDay-${mMonth+1}-$mYear"
            }, year, month, day)

            date.show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        d("bomohit", "spinner waktu: ${parent.getItemAtPosition(pos)}")
        waktu = parent.getItemAtPosition(pos).toString()
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonConfirm -> requestTazkirah(inputTazkirahTitle.text.toString(), inputTazkirahName.text.toString(), inputTazkirahDate.text.toString(), waktu)
        }
    }

    private fun requestTazkirah(title: String, name: String, date: String, waktu: String) {
        // Check if the form completed
        if (!validate()) {
            return
        }

        // Pass the data to confirmation screen
        val intent = Intent(this, RequestTazkirahConfirmation::class.java)
        intent.putExtra("waktu", waktu)
        intent.putExtra("date", date)
        intent.putExtra("title", title)
        intent.putExtra("name", name)
        startActivity(intent)

    }

    fun validate(): Boolean {
        var valid = true

        val title = inputTazkirahTitle.text.toString()
        if (title.isEmpty()) {
            inputTazkirahTitle.error = "Perlu / Required"
            valid = false
        } else {
            inputTazkirahTitle.error = null
        }

        val name = inputTazkirahName.text.toString()
        if (name.isEmpty()) {
            inputTazkirahName.error = "Perlu / Required"
            valid = false
        } else {
            inputTazkirahName.error = null
        }

        val date = inputTazkirahDate.text.toString()
        if (date.isEmpty()) {
            inputTazkirahDate.error = "Perlu / Required"
            valid = false
        } else {
            inputTazkirahDate.error = null
        }

        val waktu = waktu
        if (waktu.isEmpty()) {
            Toast.makeText(applicationContext, "Tetapkan Waktu / set the time", Toast.LENGTH_SHORT).show()
            valid = false
        } else {

        }

        return valid

    }
}