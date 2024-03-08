package com.example.asaxiycompose2.screen.audiobook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.audio_player.PlayScreen
import com.example.asaxiycompose2.screen.audioall.ByCategoryAllAudioScreen
import com.example.asaxiycompose2.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel() {
    val loadCategoryBookList = MutableSharedFlow<List<AudioDataForAdapter>>()
    val errorMessage = MutableSharedFlow<String>()
    val progress = MutableSharedFlow<Boolean>()

    fun onEventDispatcherAudio(intent: AudioIntent) {
        "onEventDispatcherAudio".myLog()
        when (intent) {
            AudioIntent.GetAllCategoryList ->
                repository.loadCategories().onEach {
                    progress.emit(true)
                    it.onSuccess { bookUIDataList ->
                        progress.emit(false)
                        bookUIDataList.size.toString().myLog()
                        loadCategoryBookList.emit(bookUIDataList)
                    }.onFailure {
                        progress.emit(false)
                        errorMessage.emit(it.message.toString())
                    }
                }.launchIn(viewModelScope)

            is AudioIntent.ClickAllAudio -> {
                viewModelScope.launch {
                    navigator.navigate(ByCategoryAllAudioScreen(intent.list))
                }
            }

            is AudioIntent.ClickItemAudio -> {
                 viewModelScope.launch{
                     navigator.navigate(PlayScreen(intent.data))
                 }
            }
        }
    }
}