package com.rodcollab.moftens.data.model

import com.rodcollab.moftens.domain.Artist

data class Song(
    val id: String,
    val name: String,
    val artists: List<Artist>
)
