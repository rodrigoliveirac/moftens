package com.rodcollab.moftens.users.topItems.track.domain

import com.rodcollab.moftens.core.service.user.TopItemTrackElement
import com.rodcollab.moftens.core.service.user.UserService
import javax.inject.Inject

class GetTopItemsTrackUseCaseImpl @Inject constructor(private val service: UserService) :
    GetTopItemsTrackUseCase {
    override suspend fun invoke(artistId: String): List<TopItemTrackElement> {
        return service.getUserTopItemsTrack().filter {
            artistId == it.artistId
        }
    }
}
