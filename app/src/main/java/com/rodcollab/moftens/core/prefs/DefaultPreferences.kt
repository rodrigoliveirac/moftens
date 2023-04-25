package com.rodcollab.moftens.core.prefs

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.rodcollab.moftens.core.util.Constants.AUTH_TOKEN
import com.rodcollab.moftens.core.util.Constants.USER_ID

class DefaultPreferences(private val sharedPref: SharedPreferences, private val editor: Editor) :
    Preferences {

    override fun saveAuthToken(response: String) {
        editor.putString(AUTH_TOKEN, response).apply()
    }

    override fun saveUserInformation(userId: String?) {
       editor.putString(USER_ID, userId).commit()
    }

    override fun setArtistIdSelected(artistId: String) {
        editor.putString(ARTIST_ID, artistId).apply()
    }

    override fun getArtistIdSelected(): String? {
        return sharedPref.getString(ARTIST_ID, "medium_term")
    }

    override fun getAuthToken(): String? {
        return sharedPref.getString(AUTH_TOKEN, "")
    }

    override fun setTimeRange(timeRange: String) {
        editor.putString(TIME_RANGE, timeRange).commit()
    }

    override fun getTimeRange(): String? {
       return sharedPref.getString(TIME_RANGE, "")
    }


}