package com.example.finalproject2.rest

import com.example.finalproject2.model.PhotoReferenceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsRetrofitService {

    @GET("place/findplacefromtext/json")
    suspend fun fetchPhotoReference(
        @Query("input") city: String,
        @Query("key") apiKey: String,
        @Query("inputtype") inputType: String = "textquery",
        @Query("fields") fields: String = "name,photos"
    ): Response<PhotoReferenceResponse>
}