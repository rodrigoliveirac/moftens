package com.rodcollab.moftens.users.topItems.artist.domain.usecase

import com.rodcollab.moftens.core.service.user.UserService
import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopItemsArtistUseCaseImpl @Inject constructor(
    private val service: UserService
) : GetTopItemsArtistUseCase {

    override suspend fun invoke(): Flow<List<TopItemArtistElement>> {

        return flow {

            val items = service.getUserTopItemsArtist().map { item ->
                TopItemArtistElement(
                    id = item.id,
                    name = item.name,
                    imgUrl = item.imgUrl
                )
            }
            emit(items)
            kotlinx.coroutines.delay(1000)
        }
    }

}
