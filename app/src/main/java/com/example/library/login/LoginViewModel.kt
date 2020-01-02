package com.example.library.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.library.utils.Event
import com.example.library.utils.LoginResult

class LoginViewModel(private val preference: SharedPreferences) : ViewModel() {

    private var userName = preference.getString(USER_NAME, NOT_SET)
    private var password = preference.getString(USER_PASSWORD, NOT_SET)

    private var _onLoginButtonClick = MutableLiveData<Event<LoginResult>>()
    val onLoginButtonClick: LiveData<Event<LoginResult>>
        get() = _onLoginButtonClick

    private var _onSingUpButtonClick = MutableLiveData<Event<LoginResult>>()
    val onSingUpButtonClick: LiveData<Event<LoginResult>>
        get() = _onSingUpButtonClick


    fun onLoginButtonClick(name: String, pass: String) {
        if (name == userName && pass == password) {
            preference.edit {
                putBoolean(USER_LOGGED_IN, true)
            }
            _onLoginButtonClick.value = Event(LoginResult.Success)
        } else {
            _onLoginButtonClick.value = Event(LoginResult.Fail)
        }

    }

    fun onSignUpButtonClick(name: String, pass: String) {
        preference.edit {
            putString(USER_NAME, name)
            putString(USER_PASSWORD, pass)
            userName = name
            password = pass
        }

        _onSingUpButtonClick.value = Event(LoginResult.Success)
    }

    companion object {
        const val USER_NAME = "user_name"
        const val USER_PASSWORD = "user_password"
        const val NOT_SET = "NOT SET"

        const val USER_LOGGED_IN = "user_logged_in"
    }
}