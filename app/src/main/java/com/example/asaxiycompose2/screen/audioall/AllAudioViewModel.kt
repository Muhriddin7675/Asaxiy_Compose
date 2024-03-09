package com.example.asaxiycompose2.screen.audioall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.audio_info.AudioInfoScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllAudioViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val repository: AppRepository
):ViewModel() {
    fun onEventDispatcher(intent:AllAudioIntent){
        when(intent){
           is AllAudioIntent.ClickItemAudio ->{
            viewModelScope.launch{
                navigator.navigate(AudioInfoScreen(intent.bookData))
            }
          }
        }

    }
}