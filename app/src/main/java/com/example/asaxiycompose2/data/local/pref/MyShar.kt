package com.example.asaxiycompose2.data.local.pref

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyShar @Inject constructor(
    private val pref:SharedPreferences
) {
    fun setHasIntroPage(number:Int){
        pref.edit().putInt("intro",number).apply()
    }
    fun getHasIntroPage() : Int  = pref.getInt("intro",0)

    fun setToken(data:String){
        pref.edit().putString("token",data).apply()
    }
    fun getToken():String = pref.getString("token","").toString()

    fun userLogin(user:Boolean){
        pref.edit().putBoolean("login",user).apply()
    }
    fun getUser():Boolean=pref.getBoolean("login",false)
    fun userNumber(user:Int){
        pref.edit().putInt("number",user).apply()
    }
    fun getNumber():Int=pref.getInt("number",0)

}