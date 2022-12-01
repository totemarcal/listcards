package com.example.listcards.presenter.features.card

import com.example.listcards.helper.intent.ViewAction

sealed class CardAction : ViewAction {
    object AllCards : CardAction()
}