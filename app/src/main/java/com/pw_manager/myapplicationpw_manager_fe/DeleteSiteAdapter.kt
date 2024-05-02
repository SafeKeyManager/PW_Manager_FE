package com.pw_manager.myapplicationpw_manager_fe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeleteSiteAdapter(private val sites: List<Site>) : RecyclerView.Adapter<DeleteSiteAdapter.SiteViewHolder>() {

    class SiteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val deleteSiteName: TextView = view.findViewById(R.id.deleteSiteName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteSiteAdapter.SiteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_item, parent, false)
        return DeleteSiteAdapter.SiteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sites.size
    }

    override fun onBindViewHolder(holder: DeleteSiteAdapter.SiteViewHolder, position: Int) {
        val site = sites[position]
        holder.deleteSiteName.text = site.siteName  // 사이트 이름 설정
        //holder.checkBox.isChecked = site.isChecked  // 체크 상태 설정

        // 체크 박스 리스너 설정
        holder.checkBox.setOnCheckedChangeListener(null)  // 리스너를 일시적으로 제거하여 무한 루프 방지
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            // 체크 상태 변경 시 데이터 모델 업데이트 (옵셔널: 외부 리스너 통보)
            sites[position].isChecked = isChecked
        }
    }

}