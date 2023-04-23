package com.rodcollab.moftens.core.service.user

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.rodcollab.moftens.core.model.User
import com.rodcollab.moftens.core.prefs.Preferences
import com.rodcollab.moftens.users.topItems.model.TopItemElement
import com.rodcollab.moftens.users.topItems.model.TopItemObject
import kotlinx.coroutines.delay
import org.json.JSONException
import javax.inject.Inject


class UserServiceImpl @Inject constructor(
    private val queue: RequestQueue,
    private val sharedPrefs: Preferences
) : UserService {

    var user: User? = null
        private set

    private val topItemElements = mutableListOf<TopItemElement>()


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

    override suspend fun getUserTopItems(): List<TopItemElement> {
        val endpoint = "$ENDPOINT/top/artists?time_range=medium_term"
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, endpoint, null,
            Response.Listener { response ->
                val gson = Gson()
                val jsonArray = response.optJSONArray("items")
                for (jsonObject in 0 until jsonArray!!.length()) {
                    try {
                        val `object` = jsonArray.getJSONObject(jsonObject).toString()
                        val topItemObject = gson.fromJson(`object`, TopItemObject::class.java)
                        val topItem = TopItemElement(
                            name = topItemObject.name,
                            imgUrl = topItemObject.images[0].url
                        )
                        topItemElements.add(topItem)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            },
            Response.ErrorListener {}) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val token = sharedPrefs.getAuthToken()
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                return headers
            }
        }
        queue.add(jsonObjectRequest)
        while (topItemElements.isEmpty()) {
            delay(100)
        }
        return topItemElements
    }

    companion object {
        private const val ENDPOINT = "https://api.spotify.com/v1/me"
    }
}