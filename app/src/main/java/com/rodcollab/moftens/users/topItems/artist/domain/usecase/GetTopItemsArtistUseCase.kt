package com.rodcollab.moftens.users.topItems.artist.domain.usecase

import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistElement
import kotlinx.coroutines.flow.Flow

interface GetTopItemsArtistUseCase {

    suspend operator fun invoke(): Flow<List<TopItemArtistElement>>
}
