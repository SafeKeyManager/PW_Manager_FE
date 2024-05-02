package com.pw_manager.myapplicationpw_manager_fe

data class SiteResponse(
    val content: List<Site> = emptyList(),
    val pageable: Pageable = Pageable(),
    val totalPages: Int = 0,
    val totalElements: Long = 0,
    val size: Int = 0,
    val number: Int = 0,
    val first: Boolean = true,
    val last: Boolean = true,
    val numberOfElements: Int = 0,
    val empty: Boolean = true
)

data class Site(
    val siteName: String,
    val siteUrl: String,
    val updateCycle: Long,
    val id: Long?,
    val createDate: String,
    val updateDate: String,
    val siteStatus: String,
    var isChecked: Boolean = false
)

data class Pageable(
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val sort: Sort = Sort(),
    val offset: Long = 0,
    val unpaged: Boolean = true,
    val paged: Boolean = false
)

data class Sort(
    val empty: Boolean = true,
    val unsorted: Boolean = true,
    val sorted: Boolean = false
)
