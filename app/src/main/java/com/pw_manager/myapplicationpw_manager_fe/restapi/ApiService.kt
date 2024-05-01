package com.pw_manager.myapplicationpw_manager_fe.restapi

import com.pw_manager.myapplicationpw_manager_fe.entity.DeleteResponse
import com.pw_manager.myapplicationpw_manager_fe.entity.SiteResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/sites")
    fun getMySiteList(
        @Query("searchTerm") searchTerm: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 10
    ): Call<SiteResponse>

    @DELETE("/site/{siteId}/delete")
    fun deleteSite(
        @Path("siteId") siteId: Long
    ): Call<DeleteResponse>
}