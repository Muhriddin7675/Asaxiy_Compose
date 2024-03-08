package com.example.asaxiycompose2.navigation

import cafe.adriel.voyager.core.screen.Screen

interface AppNavigator {
    suspend fun back()
    suspend fun navigate(screen: AndroidScreen)
    suspend fun replace(screen: AndroidScreen)
}

typealias AndroidScreen = Screen