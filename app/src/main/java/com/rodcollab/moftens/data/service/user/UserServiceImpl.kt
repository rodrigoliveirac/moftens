package com.rodcollab.moftens.data.service.user

import android.content.SharedPreferences
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.rodcollab.moftens.data.model.User


class UserServiceImpl(
    private val mQueue: RequestQueue,
    private val mSharedPreferences: SharedPreferences
) {
    var user: User? = null
        private set


    fun get(callBack: (user: User?) -> Unit) {
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            ENDPOINT,
            null,
            { response ->
                val gson = Gson()
                user = gson.fromJson(response.toString(), User::class.java)
                callBack(user)
            },
            { error ->
                callBack(null)
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                val token = mSharedPreferences.getString("token", "")
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                return headers
            }
        }
        mQueue.add(jsonObjectRequest)
    }


    companion object {
        private const val ENDPOINT = "https://api.spotify.com/v1/me"
    }
}