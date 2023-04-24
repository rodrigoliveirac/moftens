package com.rodcollab.moftens.core.model

import com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase.Album

data class TopItemTrackObject(
    val trackId: String,
    val artists: List<Artist>,
    val name: String,
    val album: Album
)
