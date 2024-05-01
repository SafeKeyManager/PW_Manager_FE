package com.pw_manager.myapplicationpw_manager_fe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pw_manager.myapplicationpw_manager_fe.R
import com.pw_manager.myapplicationpw_manager_fe.entity.Site

class DeleteSiteAdapter (private var sites: List<Site>) : RecyclerView.Adapter<DeleteSiteAdapter.SiteViewHolder>() {
    class SiteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val siteNameTextView: TextView = view.findViewById(R.id.siteNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.site_item_for_delete, parent, false)
        return SiteViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        val site = sites[position]
        holder.siteNameTextView.text = site.siteName
        holder.checkBox.isChecked = site.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            site.isChecked = isChecked
        }
    }

    override fun getItemCount() = sites.size
}