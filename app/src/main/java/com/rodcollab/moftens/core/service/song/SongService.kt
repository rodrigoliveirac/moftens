package com.rodcollab.moftens.core.service.song

import com.rodcollab.moftens.core.model.Song

interface SongService {

    suspend fun getRecentlyPlayedTracks() : List<Song>

    fun addSongToLibrary(song: Song)
}