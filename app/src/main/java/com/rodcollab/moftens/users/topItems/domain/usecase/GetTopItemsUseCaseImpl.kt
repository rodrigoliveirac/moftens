package com.rodcollab.moftens.users.topItems.domain.usecase

import com.rodcollab.moftens.core.service.user.UserService
import com.rodcollab.moftens.users.topItems.model.TopItemElement
import javax.inject.Inject

class GetTopItemsUseCaseImpl @Inject constructor(
    private val service: UserService
) : GetTopItemsUseCase {

    override suspend fun invoke(): List<TopItemElement> {
        return service.getUserTopItems().map { item ->

            TopItemElement(
                name = item.name,
                imgUrl = item.imgUrl
            )
        }
    }

}
