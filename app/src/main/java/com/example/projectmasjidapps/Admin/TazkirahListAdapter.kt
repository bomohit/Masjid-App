package com.example.projectmasjidapps.Admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmasjidapps.R
import kotlinx.android.synthetic.main.takirah_list.view.*

class TazkirahListAdapter(val tazkirah: MutableList<TazkirahList>) : RecyclerView.Adapter<TazkirahListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.viewDateTL
        val waktu: TextView = itemView.viewWaktuTL
        val title: TextView = itemView.viewTitleTL
        val name: TextView = itemView.viewNameTL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.takirah_list, parent, false)

        view.setOnClickListener {

        }


        return ViewHolder(view)
    }

    override fun getItemCount() = tazkirah.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = tazkirah[position].date
        holder.waktu.text = tazkirah[position].waktu
        holder.title.text = tazkirah[position].title
        holder.name.text = tazkirah[position].name
    }

}
