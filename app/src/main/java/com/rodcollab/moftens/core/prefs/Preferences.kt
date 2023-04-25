package com.rodcollab.moftens.core.prefs

interface Preferences {

    fun saveAuthToken(response: String)
    fun saveUserInformation(userId: String?)

    fun setArtistIdSelected(artistId: String)
    fun getArtistIdSelected(): String?

    fun getAuthToken() : String?
    fun setTimeRange(timeRange: String)
    fun getTimeRange(): String?


}