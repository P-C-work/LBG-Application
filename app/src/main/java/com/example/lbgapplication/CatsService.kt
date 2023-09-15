package com.example.lbgapplication

import com.example.lbgapplication.ui.CatsDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface CatsService {

    @Headers(
        "x-api-key: " + Constants.API_KEY,
        "Content-Type: " + Constants.TYPE,
    )
    @GET("v1/images/search")
    suspend fun fetCatsImages(
        @Query("limit") limit: Int
    ): Response<List<CatsDataModel>>

}