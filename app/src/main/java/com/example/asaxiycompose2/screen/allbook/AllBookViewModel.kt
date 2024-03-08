package com.example.asaxiycompose2.screen.allbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.allbook.AllBookIntent.ClickItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBookViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val repository: AppRepository
):ViewModel() {
    fun onEventDispatcher(intent:AllBookIntent){
        when(intent){
            is ClickItem ->{
            viewModelScope.launch{

            }
            }
        }

    }
}