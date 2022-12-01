package com.example.listcards.helper.domain

import com.example.listcards.helper.ResultX

interface UseCase <T> {
    suspend operator fun invoke(): ResultX<T>
}
