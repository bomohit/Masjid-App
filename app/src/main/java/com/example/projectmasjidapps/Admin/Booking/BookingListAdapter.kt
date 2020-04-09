package com.example.projectmasjidapps.Admin.Booking

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmasjidapps.R
import kotlinx.android.synthetic.main.admin_booking.view.*

class BookingListAdapter(val booking: MutableList<BookingList>) : RecyclerView.Adapter<BookingListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateb : TextView = itemView.viewDateB
        val event : TextView = itemView.viewEventB
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_booking, parent, false)
        val holder = ViewHolder(view)

        view.setOnClickListener {
            val intent = Intent(parent.context, AdminBookingDetail::class.java)
            intent.putExtra("date", booking[holder.adapterPosition].date)
            intent.putExtra("event", booking[holder.adapterPosition].event)
            intent.putExtra("name", booking[holder.adapterPosition].name)
            intent.putExtra("tent", booking[holder.adapterPosition].tent)
            intent.putExtra("chair", booking[holder.adapterPosition].chair)
            intent.putExtra("id", booking[holder.adapterPosition].id)
            parent.context.startActivity(intent)
        }

        return holder
    }

    override fun getItemCount() = booking.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateb.text = booking[position].date
        holder.event.text = booking[position].event
    }

}
