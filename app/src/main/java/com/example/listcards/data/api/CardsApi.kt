package com.example.listcards.data.api

import com.example.listcards.data.model.CardsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CardsApi {

    @GET("cards/types/Hero")
    suspend fun getHeros(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String,
    ): Response<List<CardsResponse>>

}