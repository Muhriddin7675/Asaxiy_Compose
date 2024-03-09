package com.example.asaxiycompose2.screen.mybook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.model.MyBooksUiData
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.audio_info.AudioInfoScreen
import com.example.asaxiycompose2.screen.book_info.BookInfoScreen
import com.example.asaxiycompose2.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookViewModel @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel() {
    val loadDataSharedFlow = MutableSharedFlow<List<MyBooksUiData>>()

    fun onEventDispatcher(intent: MyBookIntent) {
        when (intent) {
            MyBookIntent.LoadData -> {
                repository.getAllBooksFromRoom()
                    .onEach {
                        loadDataSharedFlow.emit(it)
                        "My book Screen ViewModel " + it.size.toString().myLog()
                    }
                    .launchIn(viewModelScope)
            }

            is MyBookIntent.ClickItem -> {
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
