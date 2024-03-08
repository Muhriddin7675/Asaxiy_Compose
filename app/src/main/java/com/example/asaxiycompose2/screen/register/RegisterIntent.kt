package com.example.asaxiycompose2.screen.register


interface RegisterIntent {
    data class SetEmailPasswordName(val email: String,val password : String, val name: String ) : RegisterIntent

}