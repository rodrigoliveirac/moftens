package com.rodcollab.moftens.users.topItems.artist.domain.usecase

import com.rodcollab.moftens.core.service.user.UserService
import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistElement
import javax.inject.Inject

class GetTopItemsArtistUseCaseImpl @Inject constructor(
    private val service: UserService
) : GetTopItemsArtistUseCase {

    override suspend fun invoke(): List<TopItemArtistElement> {
        return service.getUserTopItemsArtist().map { item ->

            TopItemArtistElement(
                name = item.name,
                imgUrl = item.imgUrl
            )
        }
    }

}
