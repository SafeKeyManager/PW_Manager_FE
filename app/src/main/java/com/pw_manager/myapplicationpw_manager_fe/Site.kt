package com.pw_manager.myapplicationpw_manager_fe

data class Site(
    val id: Long,
    val siteName: String,
    val siteUrl: String,
    val updateCycle: Int,
    val createDate: String,
    val updateDate: String,
    val siteStatus: String
)
