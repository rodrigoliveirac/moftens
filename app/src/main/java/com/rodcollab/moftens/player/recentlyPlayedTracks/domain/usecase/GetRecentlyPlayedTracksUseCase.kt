package com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase

import com.rodcollab.moftens.player.recentlyPlayedTracks.model.SongItem

interface GetRecentlyPlayedTracksUseCase {
    suspend operator fun invoke() : List<SongItem>
}

