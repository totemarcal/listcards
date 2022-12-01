package com.example.listcards.domain.model

data class Cards(
    val cardId : String?,
    val dbfId : Int?,
    val name : String?,
    val cardSet : String?,
    val type : String?,
    val faction : String?,
    val rarity : String?,
    val health : Int?,
    val text : String?,
    val cost : Int?,
    val attack : Int?,
    val artist : String?,
    val playerClass : String?,
    val img : String?,
    val imgGold : String?,
    val locale : String?
)