package com.rodcollab.moftens.core.service.user

import com.rodcollab.moftens.core.model.User
import com.rodcollab.moftens.users.topItems.model.TopItemElement

interface UserService {

    suspend fun get(callBack: (user: User?) -> Unit)
    suspend fun getUserTopItems() : List<TopItemElement>
}
