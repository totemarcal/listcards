package com.example.listcards.domain.repositories

import com.example.listcards.domain.model.Cards
import com.example.listcards.helper.ResultX


interface CardsRepository {
    suspend fun getHeros(): ResultX<List<Cards>>
}