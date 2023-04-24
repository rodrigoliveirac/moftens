package com.rodcollab.moftens.users.topItems.track.domain

import com.rodcollab.moftens.core.service.user.TopItemTrackElement

interface GetTopItemsTrackUseCase {

    suspend operator fun invoke(artistId: String): List<TopItemTrackElement>
}