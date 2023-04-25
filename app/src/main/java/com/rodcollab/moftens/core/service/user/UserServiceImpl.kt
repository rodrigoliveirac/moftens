package com.rodcollab.moftens.core.service.user

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.rodcollab.moftens.core.model.TopItemTrackObject
import com.rodcollab.moftens.core.model.User
import com.rodcollab.moftens.core.prefs.Preferences
import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistElement
import com.rodcollab.moftens.users.topItems.artist.model.TopItemArtistObject
import kotlinx.coroutines.delay
import org.json.JSONException
import javax.inject.Inject


class UserServiceImpl @Inject constructor(
    private val queue: RequestQueue,
    private val sharedPrefs: Preferences
) : UserService {

    var user: User? = null
        private set

    private val topItemsArtistElements = mutableListOf<TopItemArtistElement>()
    private val topItemsTrackElements = mutableListOf<TopItemTrackElement>()


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

    override suspend fun getUserTopItemsArtist(): List<TopItemArtistElement> {
        if(topItemsArtistElements.size > 0) {
            topItemsArtistElements.clear()
        }
        val endpoint = "$ENDPOINT/top/artists?time_range=${sharedPrefs.getTimeRange()}"
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, endpoint, null,
            Response.Listener { response ->
                val gson = Gson()
                val jsonArray = response.optJSONArray("items")
                for (jsonObject in 0 until jsonArray!!.length()) {
                    try {
                        val `object` = jsonArray.getJSONObject(jsonObject).toString()
                        val topItemObject = gson.fromJson(`object`, TopItemArtistObject::class.java)
                        val topItem = TopItemArtistElement(
                            name = topItemObject.name,
                            imgUrl = topItemObject.images[0].url
                        )
                        topItemsArtistElements.add(topItem)
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
        while (topItemsArtistElements.isEmpty()) {
            delay(100)
        }
        return topItemsArtistElements
    }

    override suspend fun getUserTopItemsTrack(): List<TopItemTrackElement> {
        val endpoint = "$ENDPOINT/top/tracks?time_range=medium_term"
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, endpoint, null,
            Response.Listener { response ->
                val gson = Gson()
                val jsonArray = response.optJSONArray("items")
                for (jsonObject in 0 until jsonArray!!.length()) {
                    try {
                        val `object` = jsonArray.getJSONObject(jsonObject).toString()
                        val topItemTrackObject = gson.fromJson(`object`, TopItemTrackObject::class.java)
                        lateinit var topItemTrackElement: TopItemTrackElement
                        topItemTrackObject.artists.map {
                             topItemTrackElement =  TopItemTrackElement(
                                artistId = it.id,
                                name = topItemTrackObject.name,
                                imgUrl = topItemTrackObject.album.images[0].url
                            )
                        }

                        topItemsTrackElements.add(topItemTrackElement)
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
        while (topItemsTrackElements.isEmpty()) {
            delay(100)
        }
        return topItemsTrackElements
    }

    companion object {
        private const val ENDPOINT = "https://api.spotify.com/v1/me"
    }
}