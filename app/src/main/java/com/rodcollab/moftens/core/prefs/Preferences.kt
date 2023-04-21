package com.rodcollab.moftens.core.prefs

interface Preferences {

    fun saveAuthToken(response: String)
    fun saveUserInformation(userId: String?)

    fun getAuthToken() : String?


}