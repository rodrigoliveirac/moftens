package com.rodcollab.moftens.player.recentlyPlayedTracks.domain.usecase

import com.rodcollab.moftens.player.recentlyPlayedTracks.model.SongItem
import com.rodcollab.moftens.core.model.Artist
import com.rodcollab.moftens.core.service.song.SongService
import javax.inject.Inject

class GetRecentlyPlayedTracksUseCaseImpl @Inject constructor(private val songService: SongService) :
    GetRecentlyPlayedTracksUseCase {
    override suspend fun invoke(): List<SongItem> {
        return songService.getRecentlyPlayedTracks().map {
            val artist = it.artists.map { artist ->
                Artist(
                    id = artist.id,
                    name = artist.name
                )
            }

            val imgAlbum = it.album.images[0].url

            SongItem(
                id = it.id,
                url = imgAlbum,
                name = it.name,
                artist = artist[0].name,
            )
        }
    }

}

data class ImageAlbum(
    val url: String
)

data class Album(
    val images: List<ImageAlbum>
)