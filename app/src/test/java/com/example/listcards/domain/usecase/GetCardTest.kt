package com.example.listcards.domain.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listcards.data.exception.GetCardException
import com.example.listcards.data.model.CardsResponse
import com.example.listcards.data.model.toCard
import com.example.listcards.domain.model.Cards
import com.example.listcards.domain.repositories.CardsRepository
import com.example.listcards.domain.usescases.GetCard
import com.example.listcards.helper.ResultX
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCardTest {
    private lateinit var getC: GetCard
    private lateinit var mockRepository: CardsRepository

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepository = mockk()
        getC = GetCard(mockRepository)
    }

    @Test
    fun testPegarListaEvents () = runBlocking {
        val listResponse = listOf(
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
                text = ""))
        val resultX : ResultX<List<Cards>> = ResultX.Success(listResponse.map { it.toCard() })
        coEvery { mockRepository.getHeros() } returns resultX
        //act
        val carros = getC.invoke()
        //assert
        assertTrue(carros is ResultX.Success)
        assertEquals(carros,resultX)
    }

    @Test
    fun testErroPegarListaEvent () = runBlocking {
        val result = ResultX.Failure<List<Cards>>(GetCardException())
        coEvery { mockRepository.getHeros() } returns result
        //  act
        val carros = getC.invoke()
        //assert
        assertEquals(carros,result)
    }
}