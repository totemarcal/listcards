package com.example.listcards.data.exception

import com.example.listcards.helper.exception.DomainException


class ServerException : DomainException() {
    override val message: String
        get() = "Erro interno de servidor"
}