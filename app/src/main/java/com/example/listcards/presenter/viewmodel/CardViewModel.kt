package com.example.listcards.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.listcards.data.exception.NetworkException
import com.example.listcards.helper.viewmodel.BaseViewModel
import com.example.listcards.presenter.features.card.CardAction
import com.example.listcards.presenter.features.card.CardIntent
import com.example.listcards.domain.usecases.GetCard
import com.example.listcards.helper.ResultX
import com.example.listcards.helper.network.NetworkService
import com.example.listcards.presenter.model.toUiModel
import com.example.listcards.presenter.features.card.CardState
import kotlinx.coroutines.launch

class CardViewModel(
    private val getCard: GetCard,
    private val networkService : NetworkService
) : BaseViewModel<CardIntent, CardAction, CardState>()  {

    val liveData: LiveData<CardState> get() = mState
    var id: String = ""

    override fun intentToAction(intent: CardIntent): CardAction {
        return when (intent) {
            is CardIntent.LoadAllCards -> CardAction.AllCards
        }
    }

    override fun handleAction(action: CardAction) {
        launchOnUI {
            when (action) {
                is CardAction.AllCards -> {
                    fetchCards();
                }
            }
        }
    }

    fun fetchCards() {
        viewModelScope.launch {
           if( networkService.isNetworkAvailable()) {
                mState.value = CardState.Loading(true)

                val result = getCard()
                mState.value = CardState.Loading(false)

                when (result) {
                    is ResultX.Success -> {
                        mState.value = CardState.ResultAllCards(result.value.map { event ->
                            event.toUiModel()
                        }.filter { !it.img.isNullOrBlank() })
                    }
                    is ResultX.Failure -> {
                        mState.value = CardState.Error(result.error.toString(),result.error)
                    }
                }
            } else {
                mState.value = CardState.Error(NetworkException().message)
            }
        }
    }

}