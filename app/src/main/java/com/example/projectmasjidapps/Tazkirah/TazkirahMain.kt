package com.example.projectmasjidapps.Tazkirah

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.tazkirah_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TazkirahMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tazkirah_main)

        // Initialize firestore
        val db = Firebase.firestore
        val current = LocalDate.now()

        val kuliahList = mutableListOf<kuliah>()

        db.collection("Muqaddimah").document("opening")
            .get()
            .addOnSuccessListener { ress ->
                textMuqaddimah.text = ress.getField<String>("Title")
            }

        // Check the db for kuliah
        db.collection("Tazkirah")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val parsedDate = LocalDate.parse( document.id, DateTimeFormatter.ofPattern("d-M-yyyy"))
                    if(parsedDate >= current) {
                        db.collection("Tazkirah").document(document.id).collection("Waktu Solat")
                            .get()
                            .addOnSuccessListener { res ->
                                for (doc in res) {
                                    d("bomohit", "id: ${doc.id} => title: ${doc.getField<String>("title")}")
                                    val kTitle = doc.getField<String>("title").toString()
                                    val kName = doc.getField<String>("name").toString()
                                    val kDate = doc.getField<String>("date").toString()
                                    val kWaktu = doc.getField<String>("waktu").toString()

                                    kuliahList.add(
                                        kuliah(
                                            kTitle,
                                            kName,
                                            kDate,
                                            kWaktu
                                        )
                                    )

                                    recyclerViewTazkirah.apply {
                                        layoutManager = LinearLayoutManager(this@TazkirahMain)
                                        adapter =
                                            KuliahAdapter(
                                                kuliahList
                                            )
                                    }
                                }
                            }

                    } else {
                        d("bomohit", "false")
                    }

                }
            }

    }
}