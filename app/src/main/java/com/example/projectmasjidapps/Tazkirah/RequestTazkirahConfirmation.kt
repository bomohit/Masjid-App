package com.example.projectmasjidapps.Tazkirah

import android.os.Bundle
import android.util.Log.d
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

        val waktu = intent.getStringExtra("waktu").toString()
        viewTime.text = waktu
        val date = intent.getStringExtra("date").toString()
        viewDate.text = date
        val name = intent.getStringExtra("name").toString()
        viewName.text = name
        val title = intent.getStringExtra("title").toString()
        viewTitle.text = title

        val tazkirah = hashMapOf(
            "waktu" to waktu,
            "date" to date,
            "title" to title,
            "name" to name
        )

        buttonConfirm.setOnClickListener {

            val data = HashMap<String, Any>()
            db.collection("Tazkirah Request").document(date)
                .set(data)
                .addOnSuccessListener {
                    d("bomohit", "added")
                    db.collection("Tazkirah Request").document(date).collection("Waktu Solat").document()
                        .set(tazkirah)
                        .addOnSuccessListener {
                            d("bomohit", "Tazkirah requested")
                            Toast.makeText(applicationContext, "Tazkirah berjaya dimohon", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            d("bomohit", "error request Tazkirah : ", e)
                        }
                }
        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }

    }
}