package com.example.projectmasjidapps.Admin

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.admin_list_tazkirah.*

class AdminListTazkirah : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_list_tazkirah)

        val tazkirah = mutableListOf<TazkirahList>()

        d("bomohit", "onCreate")

//        for (i in 0..5) {
//            tazkirah.add(TazkirahList("20-1-2020", "SUBUH", "Bomoh iT", "Faris"))
//        }

        //grab data from db
        db.collection("Tazkirah Request")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val date = document.id
                    d("bomohit", "date $date")
                    db.collection("Tazkirah Request").document(date).collection("Waktu Solat")
                        .get()
                        .addOnSuccessListener { res ->
                            for (doc in res) {
                                val id = doc.id
                                val name = doc.getField<String>("name").toString()
                                val title = doc.getField<String>("title").toString()
                                val waktu = doc.getField<String>("waktu").toString()

                                d("bomohit", "date: $date, waktu: $waktu, name: $name, title: $title, id: $id")
                                // Add to tazkirah
                                tazkirah.add(TazkirahList(date,waktu,title,name,id))

                                recyclerViewListTazkirah.apply {
                                    layoutManager = LinearLayoutManager(this@AdminListTazkirah)
                                    adapter = TazkirahListAdapter(tazkirah)
                                }
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                d("bomohit", "failed request ", e)
            }

    }
}