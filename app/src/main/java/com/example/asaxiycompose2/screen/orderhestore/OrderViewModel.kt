package com.example.asaxiycompose2.screen.orderhestore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.audio_info.AudioInfoScreen
import com.example.asaxiycompose2.screen.book_info.BookInfoScreen
import com.example.asaxiycompose2.screen.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator,
) : ViewModel() {
    val loadBuyBookList = MutableSharedFlow<List<BookUIData>>()
    val errorMessage = MutableSharedFlow<String>()
    fun onIntentDispatcher(intent: OrderIntent) {
        when (intent) {
            OrderIntent.loadOrderScreen -> {
                repository.getByBookIdList()
                    .onEach { result ->
                        result.onSuccess {
                            repository.getBuyBookList(it).onEach { result2 ->
                                result2.onSuccess {
                                    loadBuyBookList.emit(it)
                                }
                                result2.onFailure {
                                    errorMessage.emit(it.message.toString())
                                }
                            }.launchIn(viewModelScope)
                        }
                        result.onFailure {
                            errorMessage.emit(it.message.toString())
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is OrderIntent.ClickItem -> {
                viewModelScope.launch {
                    if (intent.bookData.type == "PDF") {
                    navigator.navigate(BookInfoScreen(intent.bookData))
                    } else {
                        navigator.navigate(AudioInfoScreen(intent.bookData))
                    }
                }
            }

        }
    }
}