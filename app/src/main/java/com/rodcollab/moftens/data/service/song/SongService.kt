package com.rodcollab.moftens.data.service.song

import com.rodcollab.moftens.data.model.Song

interface SongService {

    suspend fun getRecentlyPlayedTracks() : List<Song>

    fun addSongToLibrary(song: Song)
}