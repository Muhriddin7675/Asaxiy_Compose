package com.example.asaxiycompose2.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.local.pref.MyShar
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.intro.IntroScreen
import com.example.asaxiycompose2.screen.menu.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val myShar: MyShar,
    private val repository: AppRepository
) : ViewModel() {

    fun onEventDispatcher(intent: SplashIntent) {
        when (intent) {
            SplashIntent.Intro -> {
                viewModelScope.launch {
                    delay(1000)
                    val hasIntro = repository.getHasIntroPage()
                    if (hasIntro == 0) {
                        repository.setHasIntroPage(1)
                        navigator.replace(IntroScreen())
                    } else if (myShar.getNumber() == 1) {
                        navigator.replace(MainScreen())
                    } else {
                        navigator.replace(IntroScreen())
                    }
                }
            }
        }
    }
}
