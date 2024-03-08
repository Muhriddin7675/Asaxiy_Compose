package com.example.asaxiycompose2.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        FirebaseApp.initializeApp(this)
//        MyShar.init(this)
    }
}