package com.rodcollab.moftens.core.model

import com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase.Album

data class Song(
    val id: String,
    val name: String,
    val artists: List<Artist>,
    val album: Album
)
