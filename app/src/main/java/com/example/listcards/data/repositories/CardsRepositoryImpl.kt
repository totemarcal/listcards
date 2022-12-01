package com.example.listcards.data.repositories

import com.example.listcards.data.api.CardsApi
import com.example.listcards.data.exception.NotFoundException
import com.example.listcards.data.exception.ServerException
import com.example.listcards.data.exception.GetCardException
import com.example.listcards.data.model.toCard
import com.example.listcards.domain.model.Cards
import com.example.listcards.domain.repositories.CardsRepository
import com.example.listcards.helper.ResultX
import com.example.listcards.helper.network.ApiResponse
import com.example.listcards.helper.network.parse

class CardsRepositoryImpl (private val api: CardsApi) : CardsRepository {

    override suspend fun getHeros(): ResultX<List<Cards>> {
        val response = api.getHeros("27067eb6f3msh4f6bcd9efc25e3ap171c05jsnce11ef074b32",
            "omgvamp-hearthstone-v1.p.rapidapi.com").parse()

        return when (response) {
            is ApiResponse.Success -> {
                var listCard = response.value.map {
                    it.toCard()
                }
                ResultX.Success(listCard)
            }
            is ApiResponse.Failure -> {
                return when (response.statusCode) {
                    404 -> ResultX.Failure(NotFoundException())
                    500 -> ResultX.Failure(ServerException())
                    else -> ResultX.Failure(GetCardException())
                }
            }
        }
    }
}