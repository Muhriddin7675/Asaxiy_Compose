package com.example.asaxiycompose2.screen.audio_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.enumData.UploadData
import com.example.asaxiycompose2.data.model.AudioPlayerData
import com.example.asaxiycompose2.data.model.ProgressData
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.audio_player.PlayScreen
import com.example.asaxiycompose2.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioInfoViewModel @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel() {

    val isHasBookListener = MutableSharedFlow<Boolean>()
    val errorMessage = MutableSharedFlow<String>()
    val isHasBookBuyBtn = MutableSharedFlow<Boolean>()
    val progress = MutableSharedFlow<ProgressData>()
    val seekBarVisibility = MutableStateFlow(false)
    fun onEventDispatcher(intent: AudioIntent) {
        when (intent) {
            AudioIntent.clickBack -> {
                viewModelScope.launch {
                    navigator.back()
                }
            }

            is AudioIntent.ClickDownlandButton -> {
                val data = intent.data
                repository.downloadBook(data).onEach {
                    seekBarVisibility.emit(true)
                    when (it) {
                        UploadData.CANCEL -> {
                            errorMessage.emit("CANCEL")
                        }

                        UploadData.PAUSE -> {
                            errorMessage.emit("PAUSE")
                        }

                        UploadData.RESUME -> {
                            errorMessage.emit("RESUME")
                        }

                        is UploadData.Error -> {
                            errorMessage.emit(it.message)
                        }

                        is UploadData.Progress -> {
                            val uploadBytes = it.uploadBytes / 1024
                            val totalBytes = it.totalBytes / 1024

                            "${uploadBytes}/${totalBytes} KB   ${it.uploadBytes * 100 / it.totalBytes}%".myLog()
                            "${it.uploadBytes * 100 / it.totalBytes}".myLog()
                            progress.emit(
                                ProgressData(
                                    progress = "${uploadBytes}/${totalBytes} KB   ${it.uploadBytes * 100 / it.totalBytes}%",
                                    seekBar = (it.uploadBytes / it.totalBytes).toFloat()
                                )
                            )
                        }

                        is UploadData.SUCCESS -> {
                            errorMessage.emit("Success")
                            seekBarVisibility.emit(false)
                            onEventDispatcher(AudioIntent.HasAudioFromLocal(data.bookDocID))
                            onEventDispatcher(AudioIntent.HasAudioFromBuy(data.bookDocID, "audio"))
                        }
                    }
                }.launchIn(viewModelScope)
            }

            is AudioIntent.AddAudioBuy -> {
                val bookName = intent.bookName
                val type = intent.type
                val bookDocId = intent.bookDocId
                repository.addBookBuy(bookName, "audio", bookDocId).onEach {
                    it.onSuccess {}
                        .onFailure {}
                }.launchIn(viewModelScope)
            }

            is AudioIntent.HasAudioFromBuy -> {
                val bookDocId = intent.bookId
                val type = intent.type
                repository.bookHasBuyBooks(bookDocId)
                    .onEach {
                        it.onSuccess { result ->
                            "Book Has By Books method result : $result".myLog()
                            isHasBookBuyBtn.emit(result)
                        }.onFailure { throwable ->
                            errorMessage.emit(throwable.message.toString())
                        }
                    }.launchIn(viewModelScope)
            }

            is AudioIntent.HasAudioFromLocal -> {
                val docID = intent.bookId
                repository.hasBookFromLocal(docID).onEach {
                    it.onSuccess { bool ->
                        "Hasbook  is Succes $bool ".myLog()
                        isHasBookListener.emit(bool)
                    }.onFailure {
                        "Hasbook  is Failure ".myLog()
                    }
                }.launchIn(viewModelScope)
            }

            is AudioIntent.GetDownlandAudio -> {
                val data = intent.data
                repository.getDownloadedBook(data).onEach {
                    navigator.navigate(PlayScreen(AudioPlayerData(data,it)))
                }.launchIn(viewModelScope)
            }
        }
    }
}