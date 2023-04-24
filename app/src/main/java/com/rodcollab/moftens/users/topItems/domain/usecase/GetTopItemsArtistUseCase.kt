package com.rodcollab.moftens.users.topItems.domain.usecase

import com.rodcollab.moftens.users.topItems.model.TopItemArtistElement

interface GetTopItemsArtistUseCase {

    suspend operator fun invoke(): List<TopItemArtistElement>
}
