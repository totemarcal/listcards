package com.example.listcards.presenter.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.listcards.ListCardApplication
import com.example.listcards.core.instantLiveDataAndCoroutinesRules
import com.example.listcards.data.exception.GetCardException
import com.example.listcards.data.model.CardsResponse
import com.example.listcards.data.model.toCard
import com.example.listcards.domain.model.Cards
import com.example.listcards.domain.repositories.CardsRepository
import com.example.listcards.domain.usecases.GetCard
import com.example.listcards.helper.ResultX
import com.example.listcards.helper.network.NetworkService
import com.example.listcards.presenter.features.card.CardIntent
import com.example.listcards.presenter.features.card.CardState
import com.example.listcards.presenter.model.toUiModel
import com.example.listcards.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardsViewModelTest {
    private lateinit var viewmodel: CardViewModel
    private lateinit var getCardUseCaseMock: GetCard
    private lateinit var networkService: NetworkService
    private lateinit var cardApplication: ListCardApplication
    private lateinit var mockRepository: CardsRepository


    @ExperimentalCoroutinesApi
    @get:Rule
    val archRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = instantLiveDataAndCoroutinesRules

    private lateinit var observer: Observer<CardState>

    @Before
    fun setup() {
        mockRepository = mockk()
        getCardUseCaseMock = GetCard(mockRepository)
        cardApplication = mockk()
        networkService = mockk()
        viewmodel = CardViewModel(getCardUseCaseMock, networkService)
        observer = mockk()
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testSucessGetCards() {
        // arrange
        val mockCardsList = listOf(
            CardsResponse(cardId = "EX1_323h",
                dbfId = 492,
                name = "Lord Jaraxxus",
                cardSet = "Classic",
                type = "Hero",
                faction = "Neutral",
                rarity = "Legendary",
                cost = 0,
                attack = 0,
                health = 15,
                playerClass = "Warlock",
                img = "https://d15f34w2p8l1cc.cloudfront.net/hearthstone/2dac6266c94f06042646eb48d43b7b3a276a55a7f5faa7505e638d360f91f17c.png",
                locale = "enUS",
                artist = "",
                imgGold = "",
                text = "").toCard()
        )
        val mockCardsListUi = mockCardsList.map {
            it.toUiModel()
        }

        val result = ResultX.Success(mockCardsList)

        every { observer.onChanged(any()) }.returns(Unit)
        every { networkService.isNetworkAvailable() }.returns(true)
        coEvery { getCardUseCaseMock.invoke() } returns result

        viewmodel.liveData.observeForever(observer)
        viewmodel.dispatchIntent(CardIntent.LoadAllCards)

        // Verify
        verifyOrder {
            observer.onChanged(CardState.Loading(true))
            observer.onChanged(CardState.Loading(false))
            observer.onChanged(CardState.ResultAllCards(mockCardsListUi))
        }
    }

    @Test
    fun testErrorGetCards() {
        // arrange
        val result = ResultX.Failure<List<Cards>>(GetCardException())
        every { observer.onChanged(any()) }.returns(Unit)
        every { networkService.isNetworkAvailable() }.returns(true)
        coEvery { getCardUseCaseMock() } returns result
        viewmodel.liveData.observeForever(observer)

        // act
        runBlocking { viewmodel.fetchCards() }

        // assert
        verifyOrder {
            observer.onChanged(CardState.Loading(true))
            observer.onChanged(CardState.Loading(false))
            observer.onChanged(CardState.Error(result.exceptionOrNull().toString(),
                result.exceptionOrNull()!!
            ))
        }
    }

    @Test
    fun testNetworkError() {
        every { observer.onChanged(any()) }.returns(Unit)
        every { networkService.isNetworkAvailable() }.returns(false)
        runBlocking { viewmodel.fetchCards() }
        verifyOrder {
            observer.onChanged(CardState.Error("Internet indisponível"))
        }
    }
}