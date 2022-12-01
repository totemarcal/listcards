package com.example.listcards.data.exception

import com.example.listcards.helper.exception.DomainException

class NetworkException : DomainException(){
    override val message: String
        get() = "Internet indisponível"
}