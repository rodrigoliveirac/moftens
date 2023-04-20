package com.rodcollab.moftens.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodcollab.moftens.data.Preferences
import com.rodcollab.moftens.data.service.user.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val sharedPrefs: Preferences,
    private val userService: UserService
) : ViewModel() {


    private val hasUser: MutableLiveData<UserInformationStatusState> by lazy {
        MutableLiveData<UserInformationStatusState>()
    }

    fun hasUser(): LiveData<UserInformationStatusState> {
        return hasUser
    }

    data class UserInformationStatusState(val hasUser: Boolean)

    fun saveAuthToken(accessToken: String?) {
        viewModelScope.launch {
            sharedPrefs.saveAuthToken(accessToken.toString())

            userService.get() { user ->
                if (user != null) {
                    val id = user.id
                    sharedPrefs.saveUserInformation(id)
                    hasUser.postValue(UserInformationStatusState(true))
                } else {
                    Log.d("ERROR", "SOMETHING IS WRONG")
                }
            }
        }
    }
}