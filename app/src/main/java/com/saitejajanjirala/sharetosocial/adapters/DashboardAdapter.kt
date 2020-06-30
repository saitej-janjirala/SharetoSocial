package com.saitejajanjirala.sharetosocial.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.sharetosocial.R

class DashboardAdapter(val context:Context) :RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(){
    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.dashboarditem,parent,false)
        return DashboardViewHolder(view)
    }
    override fun getItemCount(): Int {
        return 10
    }
    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        
    }
}