package com.example.listcards.data.model

import com.example.listcards.domain.model.Cards

data class CardsResponse (
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
fun CardsResponse.toCard() = Cards(
    cardId = this.cardId,
    dbfId = this.dbfId,
    name = this.name,
    cardSet = this.cardSet,
    type = this.type,
    faction = this.faction,
    rarity = this.rarity,
    health = this.health,
    text = this.text,
    cost = this.cost,
    attack = this.attack,
    artist = this.artist,
    playerClass = this.playerClass,
    img = this.img,
    imgGold = this.imgGold,
    locale = this.locale
)
