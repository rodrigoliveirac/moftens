package com.rodcollab.moftens.data

interface Preferences {

    fun getAuthToken(response: String)
    fun getUserInformation(userId: String?)
}