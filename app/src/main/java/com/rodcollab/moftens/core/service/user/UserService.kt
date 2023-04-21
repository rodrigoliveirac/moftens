package com.rodcollab.moftens.core.service.user

import com.rodcollab.moftens.core.model.User

interface UserService {

    suspend fun get(callBack: (user: User?) -> Unit)
}
