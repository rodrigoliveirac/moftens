package com.rodcollab.moftens.data

interface Preferences {

    fun saveAuthToken(response: String)
    fun saveUserInformation(userId: String?)

    fun getAuthToken() : String?


}