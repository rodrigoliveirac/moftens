package com.rodcollab.moftens.data

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.rodcollab.moftens.Constants.AUTH_TOKEN

class DefaultPreferences(private val sharedPref: SharedPreferences) : Preferences {

    override fun saveAuthToken(response: String) {
        sharedPref.edit().putString(AUTH_TOKEN, response).apply()
    }

    override fun saveUserInformation(userId: String?) : Editor {
       return  sharedPref.edit()
    }

    override fun getAuthToken() : String? { return sharedPref.getString("token", "") }


}