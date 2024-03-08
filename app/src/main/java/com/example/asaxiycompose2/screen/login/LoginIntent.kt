package com.example.asaxiycompose2.screen.login

import android.util.Log

interface LoginIntent {
//    data object OpenMenuScreen:LoginIntent
    data class SetEmailPassword(val email: String,val password : String ) : LoginIntent
    data object OpenRegisterScreen : LoginIntent
}