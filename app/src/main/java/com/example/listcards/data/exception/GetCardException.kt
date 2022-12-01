package com.example.listcards.data.exception

import com.example.listcards.helper.exception.DomainException

class GetCardException : DomainException(){
    override val message: String
        get() = "Erro de regra de negócio"
}