package com.rodcollab.moftens.users.topItems.artist.domain.usecase

import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistElement

interface GetTopItemsArtistUseCase {

    suspend operator fun invoke(): List<TopItemArtistElement>
}
