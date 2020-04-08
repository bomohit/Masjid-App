package com.example.projectmasjidapps.Tazkirah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmasjidapps.R
import kotlinx.android.synthetic.main.tazkirah_list.view.*

class KuliahAdapter(private val kuliahList: MutableList<kuliah>) : RecyclerView.Adapter<KuliahAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.textDate
        val waktu: TextView = itemView.textWaktu
        val title: TextView = itemView.textTitle
        val name: TextView = itemView.textName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tazkirah_list, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount() = kuliahList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = kuliahList[position].date
        holder.waktu.text = kuliahList[position].waktu
        holder.title.text = kuliahList[position].title
        holder.name.text = kuliahList[position].name
    }

}
