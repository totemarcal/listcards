package com.example.listcards.domain.usescases

import com.example.listcards.helper.domain.UseCase
import com.example.listcards.domain.model.Cards
import com.example.listcards.domain.repositories.CardsRepository

class GetCard(private val rep: CardsRepository) : UseCase<List<Cards>> {
    override suspend fun invoke() = rep.getHeros()
}