package com.example.dictionary.models.api

import com.example.dictionary.models.Words
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("en/{word}")
    suspend fun getMeaning(@Path("word") word : String) : Response<List<Words>>
}