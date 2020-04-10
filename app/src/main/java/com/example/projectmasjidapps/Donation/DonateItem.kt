package com.example.projectmasjidapps.Donation


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmasjidapps.R
import kotlinx.android.synthetic.main.donate_item.*
class DonateItem : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_item)

        buttonNext.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.buttonNext -> passForConfirmation(inputNameDi.text.toString(), inputItemDi.text.toString(), inputAddressDi.text.toString())
            R.id.buttonCancel -> onBackPressed()
        }
    }

    private fun passForConfirmation(name: String, item: String, address: String) {

        // check if the form completed or not
        if (!validate()) {
            return
        }

        val intent = Intent(this, DonateItemConfirmation::class.java)
        intent.putExtra("name", name)
        intent.putExtra("item", item)
        intent.putExtra("address", address)
        startActivity(intent)

    }

    fun validate(): Boolean {
        var valid = true

        val name = inputNameDi.text.toString()
        if (name.isEmpty()) {
            inputNameDi.error = "Perlu / Required"
            valid = false
        } else {
            inputNameDi.error = null
        }
        val item = inputItemDi.text.toString()
        if (item.isEmpty()) {
            inputItemDi.error = "Perlu / Required"
            valid = false
        } else {
            inputItemDi.error = null
        }
        val address = inputAddressDi.text.toString()
        if (address.isEmpty()) {
            inputAddressDi.error = "Perlu / Required"
            valid = false
        } else {
            inputAddressDi.error = null
        }

        return valid
    }
}