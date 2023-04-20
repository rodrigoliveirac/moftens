package com.rodcollab.moftens.data.service.song

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.rodcollab.moftens.data.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class SongServiceImpl(context: Context,) : SongService {

    private val songs =  mutableListOf<Song>()
    private val sharedPreferences: SharedPreferences
    private val queue: RequestQueue

    init {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0)
        queue = Volley.newRequestQueue(context)
    }

    override suspend fun getRecentlyPlayedTracks(): List<Song> = withContext(Dispatchers.IO) {
        val endpoint = "https://api.spotify.com/v1/me/player/recently-played"

        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.GET, endpoint, null,
                Response.Listener { response: JSONObject ->
                    val gson = Gson()
                    val jsonArray = response.optJSONArray("items")
                    for (n in 0 until jsonArray.length()) {
                        try {
                            var `object` = jsonArray.getJSONObject(n)
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
                    val token = sharedPreferences.getString("token", "")
                    val auth = "Bearer $token"
                    headers["Authorization"] = auth
                    return headers
                }
            }

        queue.add(jsonObjectRequest)
        while (songs.isEmpty()) {
            delay(100)
        }
        return@withContext songs
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
                val token = sharedPreferences.getString("token", "")
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
