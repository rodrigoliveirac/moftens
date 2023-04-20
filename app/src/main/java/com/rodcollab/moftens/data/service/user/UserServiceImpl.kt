package com.rodcollab.moftens.data.service.user

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.rodcollab.moftens.data.Preferences
import com.rodcollab.moftens.data.model.User
import javax.inject.Inject


class UserServiceImpl @Inject constructor(
    private val queue: RequestQueue,
    private val sharedPrefs: Preferences
) : UserService {

    var user: User? = null
        private set


    override suspend fun get(callBack: (user: User?) -> Unit) {
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
                val token = sharedPrefs.getAuthToken()
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }

    companion object {
        private const val ENDPOINT = "https://api.spotify.com/v1/me"
    }
}