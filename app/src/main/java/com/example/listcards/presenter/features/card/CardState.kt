package com.example.listcards.presenter.features.card

import com.example.listcards.helper.intent.ViewState
import com.example.listcards.presenter.model.CardsUiModel

sealed class CardState : ViewState {
    open class Loading(val loading: Boolean) : CardState()
    data class ResultAllCards(val data : List<CardsUiModel>): CardState()
    data class Error(val msg: String, val error: Throwable? = null) : CardState()
}