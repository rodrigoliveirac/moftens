package com.rodcollab.moftens.core.service.song

import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.rodcollab.moftens.core.model.Song
import com.rodcollab.moftens.core.prefs.Preferences
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


class SongServiceImpl @Inject constructor(private val queue: RequestQueue, private val sharedPrefs: Preferences) :
    SongService {

    private val mQueue: RequestQueue = queue
    private val songs =  mutableListOf<Song>()

    override suspend fun getRecentlyPlayedTracks(): List<Song>  {
        val endpoint = "https://api.spotify.com/v1/me/player/recently-played"

        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, endpoint, null,
                Response.Listener { response: JSONObject ->
                    val gson = Gson()
                    val jsonArray = response.optJSONArray("items")
                    for (jsonObject in 0 until jsonArray!!.length()) {
                        try {
                            var `object` = jsonArray.getJSONObject(jsonObject)
                            `object` = `object`.optJSONObject("track")
                            val song =
                                gson.fromJson(`object`.toString(), Song::class.java)
                            songs.add(song)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                },
                Response.ErrorListener { error: VolleyError? ->
                    throw IOException(error?.message ?: "Unknown error occurred")
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: MutableMap<String, String> = HashMap()
                    val token = sharedPrefs.getAuthToken()
                    val auth = "Bearer $token"
                    headers["Authorization"] = auth
                    return headers
                }
            }

        mQueue.add(jsonObjectRequest)
        while (songs.isEmpty()) {
            delay(100)
        }
        return songs
    }

    override fun addSongToLibrary(song: Song) {
        val payload = preparePutPayload(song)
        val jsonObjectRequest = prepareSongLibraryRequest(payload)
        queue.add(jsonObjectRequest)
    }

    private fun prepareSongLibraryRequest(payload: JSONObject): JsonObjectRequest {
        return object : JsonObjectRequest(
            Method.PUT, "https://api.spotify.com/v1/me/tracks", payload,
            Response.Listener { response: JSONObject? -> },
            Response.ErrorListener { error: VolleyError? -> }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val token = sharedPrefs.getAuthToken()
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
    }

    private fun preparePutPayload(song: Song): JSONObject {
        val idArray = JSONArray()
        idArray.put(song.id)
        val ids = JSONObject()
        try {
            ids.put("ids", idArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ids
    }
}
