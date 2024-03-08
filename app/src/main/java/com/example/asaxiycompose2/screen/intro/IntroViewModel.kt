package com.example.asaxiycompose2.screen.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {

    fun onEventDispatcher(intent: IntroIntent) {
        when (intent) {
            IntroIntent.OpenLogin -> {
                viewModelScope.launch {
                    navigator.replace(LoginScreen())
                }
            }
        }
    }
}