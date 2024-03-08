package com.example.asaxiycompose2.screen.menu

import androidx.lifecycle.ViewModel
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val  navigator: AppNavigator,
    private val  repository: AppRepository
):ViewModel() {


    fun  onEventDispatcher(intent:MainIntent){

    }
}