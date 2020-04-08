package com.example.projectmasjidapps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.flow_statent.view.*

class MoneyFlowStatementAdapter(val statementList: MutableList<statement>) : RecyclerView.Adapter<MoneyFlowStatementAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lName: TextView = itemView.viewName
        val total: TextView = itemView.viewTotal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flow_statent, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount() = statementList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lName.text = statementList[position].name
        holder.total.text = statementList[position].total
    }

}
