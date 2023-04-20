package com.rodcollab.moftens.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.rodcollab.moftens.Constants.CLIENT_ID
import com.rodcollab.moftens.Constants.REDIRECT_URI
import com.rodcollab.moftens.Constants.REQUEST_CODE
import com.rodcollab.moftens.Constants.SCOPES
import com.rodcollab.moftens.Constants.SPOTIFY_PREFS
import com.rodcollab.moftens.MainActivity
import com.rodcollab.moftens.data.DefaultPreferences
import com.rodcollab.moftens.data.Preferences
import com.rodcollab.moftens.data.service.user.UserServiceImpl
import com.rodcollab.moftens.databinding.ActivityAuthorizationBinding
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private var mSharedPreferences: SharedPreferences? = null

    private lateinit var sharedPrefs: Preferences

    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = DefaultPreferences(getSharedPreferences(SPOTIFY_PREFS, 0))

        binding.button.setOnClickListener {
            authenticateSpotify()
            mSharedPreferences = getSharedPreferences(SPOTIFY_PREFS, 0)
            queue = Volley.newRequestQueue(this);
        }

    }

    private fun authenticateSpotify() {
        val builder =
            AuthorizationRequest.Builder(
                CLIENT_ID, AuthorizationResponse.Type.TOKEN,
               REDIRECT_URI
            )
        builder.setScopes(arrayOf<String>(SCOPES))
        val request = builder.build()
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response: AuthorizationResponse =
                AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    getAuthToken(response)
                    waitForUserInfo()
                }
                AuthorizationResponse.Type.ERROR -> {

                }
                else -> {}
            }
        }
    }

    private fun getAuthToken(response: AuthorizationResponse) {
        sharedPrefs.getAuthToken(response.accessToken)
    }

    private fun waitForUserInfo() {
        val userService = UserServiceImpl(queue!!, mSharedPreferences!!)
        userService.get() {user ->
            if (user != null) {

                val id = userService.user?.id
                sharedPrefs.getUserInformation(id)
                startMainActivity()
            } else {
                Log.d("ERROR", "SOMETHING IS WRONG")
            }
        }
    }

    private fun startMainActivity() {
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
    }
}