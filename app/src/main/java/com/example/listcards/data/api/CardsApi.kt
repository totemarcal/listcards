package com.example.listcards.data.api

import com.example.listcards.data.model.CardsResponse
import retrofit2.Response
import retrofit2.http.GET

interface CardsApi {

    @GET("cards/types/Hero")
    suspend fun getHeros(): Response<List<CardsResponse>>

}