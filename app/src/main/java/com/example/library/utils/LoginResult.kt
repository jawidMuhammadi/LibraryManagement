package com.example.library.utils

sealed class LoginResult {
    object Success : LoginResult()
    object Fail : LoginResult()
}