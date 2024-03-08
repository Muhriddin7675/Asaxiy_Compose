package com.example.asaxiycompose2.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiycompose2.data.local.pref.MyShar
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.intro.IntroScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val myShar: MyShar
):ViewModel() {

    fun onEventDispatcher(intent:SplashIntent){
        when(intent){
           SplashIntent.Intro -> {
               viewModelScope.launch {
                   delay(1000)
                   navigator.navigate(IntroScreen())
               }
           }
        }
    }
}
/*
 if (hasIntro == 0){
                repository.setHasIntroPage(1)
                openIntroLiveData.value = Unit
            }
            else if(mayShar.getNumber()==1){
                openMainLiveData.value = Unit
            }else{
                openLogin.value=Unit

            }
 */