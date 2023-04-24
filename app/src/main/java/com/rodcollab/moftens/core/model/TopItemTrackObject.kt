package com.rodcollab.moftens.core.model

data class TopItemTrackObject(
    val trackId: String,
    val artists: List<Artist>,
    val name: String
)
