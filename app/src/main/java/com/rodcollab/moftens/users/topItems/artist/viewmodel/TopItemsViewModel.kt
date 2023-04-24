package com.rodcollab.moftens.users.topItems.artist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.moftens.users.topItems.artist.domain.usecase.GetTopItemsArtistUseCase
import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopItemsViewModel @Inject constructor(private val getTopItemsUseCase: GetTopItemsArtistUseCase) : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData(UiState(list = emptyList()))
    }

    fun streamAndOnce(): LiveData<UiState> {
        return uiState
    }

    fun onResume() {
        viewModelScope.launch {
            uiState.postValue(UiState(getTopItemsUseCase()))
        }
    }


    data class UiState(val list: List<TopItemArtistElement>)
}