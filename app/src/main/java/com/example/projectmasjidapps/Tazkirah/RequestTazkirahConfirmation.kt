package com.example.projectmasjidapps.Tazkirah

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.request_tazkirah_confirmation.*

class RequestTazkirahConfirmation : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_tazkirah_confirmation)

        progressBarRequestTazkirah.visibility = View.VISIBLE

        val waktu = intent.getStringExtra("waktu").toString()
        viewTime.text = waktu
        val date = intent.getStringExtra("date").toString()
        viewDate.text = date
        val name = intent.getStringExtra("name").toString()
        viewName.text = name
        val title = intent.getStringExtra("title").toString()
        viewTitle.text = title

        progressBarRequestTazkirah.visibility = View.INVISIBLE

        val tazkirah = hashMapOf(
            "waktu" to waktu,
            "date" to date,
            "title" to title,
            "name" to name
        )

        fun whenDone() {
            val intent = Intent(this, TazkirahMain::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        buttonConfirm.setOnClickListener {
            progressBarRequestTazkirah.visibility = View.VISIBLE

            val data = HashMap<String, Any>()
            db.collection("Tazkirah Request").document(date)
                .set(data)
                .addOnSuccessListener {
                    d("bomohit", "added")
                    db.collection("Tazkirah Request").document(date).collection("Waktu Solat").document()
                        .set(tazkirah)
                        .addOnSuccessListener {
                            d("bomohit", "Tazkirah requested")
                            progressBarRequestTazkirah.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext, "Permohonan berjaya dimohon", Toast.LENGTH_SHORT).show()
                            whenDone()
                        }
                        .addOnFailureListener { e ->
                            d("bomohit", "error request Tazkirah : ", e)
                        }
                }
        }

        buttonCancel.setOnClickListener {
            Toast.makeText(applicationContext, "Permohonan dibatalkan", Toast.LENGTH_SHORT).show()
            whenDone()
        }

    }
}