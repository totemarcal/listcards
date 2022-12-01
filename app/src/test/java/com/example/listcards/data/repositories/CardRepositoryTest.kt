package com.example.listcards.data.repositories

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listcards.data.api.CardsApi
import com.example.listcards.data.exception.NotFoundException
import com.example.listcards.data.exception.ServerException
import com.example.listcards.data.model.CardsResponse
import com.example.listcards.data.model.toCard
import com.example.listcards.helper.ResultX
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class CardRepositoryTest {
    private lateinit var rep: CardsRepositoryImpl
    private lateinit var eventApi: CardsApi
    private lateinit var responseEvent : Response<List<CardsResponse>>

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        eventApi = mockk()
        responseEvent = mockk()
        rep = CardsRepositoryImpl(eventApi)
    }

    @Test
    fun testGetCard () = runBlocking {
        // arrange
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
        val response = Response.success(listResponse)
        val listCarros = listResponse.map { it.toCard() }
        coEvery { eventApi.getHeros("27067eb6f3msh4f6bcd9efc25e3ap171c05jsnce11ef074b32",
            "omgvamp-hearthstone-v1.p.rapidapi.com") } returns response
        //act
        val result = rep.getHeros()
        //assert
        assertTrue(result is ResultX.Success)
        assertEquals(result.getOrNull(), listCarros)
    }

    @Test
    fun test404PegarListaCarros () = runBlocking {
        // arrange
        every { responseEvent.isSuccessful } returns false
        coEvery { eventApi.getHeros("27067eb6f3msh4f6bcd9efc25e3ap171c05jsnce11ef074b32",
            "omgvamp-hearthstone-v1.p.rapidapi.com") } returns Response.error(404, ResponseBody.create(null,""))
        //act
        val result = rep.getHeros()
        //assert
        assertTrue(result is ResultX.Failure)
        assertTrue(result.exceptionOrNull() is NotFoundException)
    }

    @Test
    fun test500PegarListaCarros () = runBlocking {
        // arrange
        every { responseEvent.isSuccessful } returns false
        coEvery { eventApi.getHeros("27067eb6f3msh4f6bcd9efc25e3ap171c05jsnce11ef074b32",
            "omgvamp-hearthstone-v1.p.rapidapi.com") } returns Response.error(500, ResponseBody.create(null,""))
        //act
        val result = rep.getHeros()
        //assert
        assertTrue(result is ResultX.Failure)
        assertTrue(result.exceptionOrNull() is ServerException)
    }

}