package com.rodcollab.moftens.data.service.user

import com.rodcollab.moftens.data.model.User

interface UserService {

    suspend fun get(callBack: (user: User?) -> Unit)
}
