package com.example.asaxiybooks.domain

import com.example.asaxiycompose2.data.model.LoginUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun createUser(email:String,password:String,name:String):Flow<Result<String>>
    fun addUserLogin(email:String,password:String):Flow<Boolean>
    fun setUser(data1: LoginUser):Flow<Unit>



}