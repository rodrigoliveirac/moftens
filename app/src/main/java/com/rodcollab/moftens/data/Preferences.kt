package com.rodcollab.moftens.data

import android.content.SharedPreferences.Editor

interface Preferences {

    fun saveAuthToken(response: String)
    fun saveUserInformation(userId: String?) : Editor

    fun getAuthToken() : String?


}