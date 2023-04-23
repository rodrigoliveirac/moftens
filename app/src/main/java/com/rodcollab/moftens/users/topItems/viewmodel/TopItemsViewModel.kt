package com.rodcollab.moftens.users.topItems.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.moftens.users.topItems.domain.usecase.GetTopItemsUseCase
import com.rodcollab.moftens.users.topItems.model.TopItemElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopItemsViewModel @Inject constructor(private val getTopItemsUseCase: GetTopItemsUseCase) : ViewModel() {

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


    data class UiState(val list: List<TopItemElement>)
}