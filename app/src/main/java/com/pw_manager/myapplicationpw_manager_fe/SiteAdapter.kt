package com.pw_manager.myapplicationpw_manager_fe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SiteAdapter(private val sites: List<Site>) : RecyclerView.Adapter<SiteAdapter.SiteViewHolder>() {
    class SiteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val siteNameTextView: TextView = view.findViewById(R.id.siteNameTextView)
        val updateDateTextView: TextView = view.findViewById(R.id.updateDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.site_item, parent, false)
        return SiteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sites.size
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        val site = sites[position]
        holder.siteNameTextView.text = site.siteName
        holder.updateDateTextView.text = site.updateDate
    }
}