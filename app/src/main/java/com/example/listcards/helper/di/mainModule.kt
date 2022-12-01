package com.example.listcards.helper.di

import com.example.listcards.data.api.CardsApi
import com.example.listcards.data.repositories.CardsRepositoryImpl
import com.example.listcards.domain.repositories.CardsRepository
import com.example.listcards.domain.usecases.GetCard
import com.example.listcards.helper.network.NetworkService
import com.example.listcards.helper.network.NetworkServiceImpl
import com.example.listcards.helper.network.RetrofitService
import com.example.listcards.presenter.viewmodel.CardViewModel
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitService().createService(CardsApi::class.java) }

    single<CardsRepository> { CardsRepositoryImpl(get()) }

    single<NetworkService> { NetworkServiceImpl() }

    single { GetCard(get()) }

    factory { CardViewModel(get(), get()) }

}
