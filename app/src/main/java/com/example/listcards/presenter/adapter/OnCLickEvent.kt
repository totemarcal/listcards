package com.example.listcards.presenter.adapter

import com.example.listcards.presenter.model.CardsUiModel


interface OnCLickEvent {
    fun onClickEvent(eventUiModel: CardsUiModel)
}