package com.rodcollab.moftens.users.topItems.domain.usecase

import com.rodcollab.moftens.users.topItems.model.TopItemElement

interface GetTopItemsUseCase {

    suspend operator fun invoke(): List<TopItemElement>
}
