package com.example.listcards.presenter.features.card

import com.example.listcards.helper.intent.ViewIntent

sealed class CardIntent : ViewIntent {
    object LoadAllCards : CardIntent()
}