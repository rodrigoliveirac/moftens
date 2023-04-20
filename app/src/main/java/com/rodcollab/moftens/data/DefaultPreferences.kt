package com.rodcollab.moftens.data

import android.content.SharedPreferences
import com.rodcollab.moftens.Constants.AUTH_TOKEN
import com.rodcollab.moftens.Constants.USER_ID

class DefaultPreferences(private val sharedPref: SharedPreferences) : Preferences {

    override fun getAuthToken(response: String) {
        sharedPref.edit().putString(AUTH_TOKEN, response).apply()
    }

    override fun getUserInformation(userId: String?) {
        sharedPref.edit().putString(USER_ID, userId).commit()
    }


}