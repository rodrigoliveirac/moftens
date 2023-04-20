package com.rodcollab.moftens.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rodcollab.moftens.BuildConfig
import com.rodcollab.moftens.MainActivity
import com.rodcollab.moftens.databinding.ActivityAuthorizationBinding
import com.rodcollab.moftens.util.Constants.REQUEST_CODE
import com.rodcollab.moftens.util.Constants.SCOPES
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthorizationViewModel::class.java]

        binding.button.setOnClickListener {
            authenticateSpotify()
        }

    }

    private fun authenticateSpotify() {
        val builder =
            AuthorizationRequest.Builder(
                BuildConfig.CLIENT_ID, AuthorizationResponse.Type.TOKEN,
               BuildConfig.REDIRECT_URI
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
        viewModel.saveAuthToken(response.accessToken)
    }

    private fun waitForUserInfo() {
        viewModel.hasUser().observe(this) { hasUserState ->
            if(hasUserState.hasUser) {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
    }
}