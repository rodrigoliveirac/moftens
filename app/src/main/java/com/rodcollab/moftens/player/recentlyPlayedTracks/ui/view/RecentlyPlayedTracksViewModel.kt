package com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view

import androidx.lifecycle.*
import com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase.GetRecentlyPlayedTracksUseCase
import com.rodcollab.moftens.player.recentlyPlayedTracks.model.SongItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyPlayedTracksViewModel @Inject constructor(
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

}