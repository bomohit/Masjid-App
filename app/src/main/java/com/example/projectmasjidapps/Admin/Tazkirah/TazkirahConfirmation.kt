package com.example.projectmasjidapps.Admin.Tazkirah

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.Admin.AdminListTazkirah
import com.example.projectmasjidapps.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.admin_tazkirah_confirmation.*

class TazkirahConfirmation : AppCompatActivity(), View.OnClickListener {

    val db = Firebase.firestore

    var documentID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_tazkirah_confirmation)

        val name = intent.getStringExtra("name")
        val waktu = intent.getStringExtra("waktu")
        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val id = intent.getStringExtra("id").toString()
        documentID = id

        viewNameTC.text = name
        viewTitleTC.text = title
        viewWaktuTC.text = waktu
        viewDateTC.text = date

        buttonAccept.setOnClickListener(this)
        buttonReject.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonAccept -> approveRequest(viewNameTC.text.toString(), viewTitleTC.text.toString(), viewWaktuTC.text.toString(), viewDateTC.text.toString(), documentID)

            R.id.buttonReject -> rejectRequest(viewNameTC.text.toString(), viewTitleTC.text.toString(), viewWaktuTC.text.toString(), viewDateTC.text.toString(), documentID)
        }
    }

    private fun rejectRequest(
        name: String,
        title: String,
        waktu: String,
        date: String,
        id: String
    ) {
        // Delete from Tazkirah Request
        db.collection("Tazkirah Request").document(date).collection("Waktu Solat").document(id)
            .delete()
            .addOnSuccessListener {
                d("bomohit", "deleted")
                Toast.makeText(applicationContext, "Request Rejected", Toast.LENGTH_SHORT).show()
                whenDone()
            }
            .addOnFailureListener { e ->
                d("bomohit", "delete error: ", e)
            }
    }

    private fun approveRequest(name: String, title: String, waktu: String, date: String, id: String) {
        val data = HashMap<String, Any>()

        val tazkirah = hashMapOf(
            "waktu" to waktu,
            "date" to date,
            "title" to title,
            "name" to name
        )

        db.collection("Tazkirah").document(date)
            .set(data)
            .addOnSuccessListener {
                d("bomohit", "added")
                db.collection("Tazkirah").document(date).collection("Waktu Solat").document(waktu)
                    .set(tazkirah)
                    .addOnSuccessListener {
                        d("bomohit", "Tazkirah has been Approve")

                        // Delete from Tazkirah Request
                        db.collection("Tazkirah Request").document(date).collection("Waktu Solat").document(id)
                            .delete()
                            .addOnSuccessListener {
                                d("bomohit", "deleted")
                                Toast.makeText(applicationContext, "Request Approved", Toast.LENGTH_SHORT).show()
                                whenDone()
                            }
                            .addOnFailureListener { e ->
                                d("bomohit", "delete error: ", e)
                            }

                    }
                    .addOnFailureListener { e ->
                        d("bomohit", "error approving request : ", e)
                    }
            }
            .addOnFailureListener { e ->
                d("bomoh it", "error creating document: ", e)
            }

    }

    fun whenDone() {
        val intent = Intent(this, AdminListTazkirah::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}