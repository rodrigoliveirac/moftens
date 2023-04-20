package com.rodcollab.moftens.ui.recentlyPlayedTracks

import androidx.lifecycle.*
import com.rodcollab.moftens.domain.GetRecentlyPlayedTracksUseCase
import com.rodcollab.moftens.domain.SongItem
import kotlinx.coroutines.launch

class RecentlyPlayedTracksViewModel(
    private val getRecentlyPlayedTracksUseCase: GetRecentlyPlayedTracksUseCase
) : ViewModel() {



    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(list = emptyList()))
    }

    fun streamAndOnce(): LiveData<UiState>{
        return uiState
    }

    fun onResume() {
        viewModelScope.launch {
            uiState.postValue(UiState(list = getRecentlyPlayedTracksUseCase()))
        }
    }

    data class UiState(val list: List<SongItem>)

    @Suppress("UNCHECKED_CAST")
        class Factory(private val getRecentlyPlayedTracksUseCase: GetRecentlyPlayedTracksUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecentlyPlayedTracksViewModel(getRecentlyPlayedTracksUseCase) as T
        }
    }
}