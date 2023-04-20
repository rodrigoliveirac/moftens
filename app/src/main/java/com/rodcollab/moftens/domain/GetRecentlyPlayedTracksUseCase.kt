package com.rodcollab.moftens.domain

import com.rodcollab.moftens.data.service.song.SongService

interface GetRecentlyPlayedTracksUseCase {
    suspend operator fun invoke() : List<SongItem>
}

class GetRecentlyPlayedTracksUseCaseImpl(private val songService: SongService) :
    GetRecentlyPlayedTracksUseCase {
    override suspend fun invoke(): List<SongItem> {
         return songService.getRecentlyPlayedTracks().map {
            val artist = it.artists.map { artist ->
                 Artist(
                     id = artist.id,
                     name = artist.name
                 )
             }
             SongItem(
                 id = it.id,
                 name = it.name,
                 artist = artist[0].name
             )
         }
    }

}

data class SongItem(
    val id: String,
    val name: String,
    val artist: String
)

data class Artist(
    val id: String,
    val name: String
)

