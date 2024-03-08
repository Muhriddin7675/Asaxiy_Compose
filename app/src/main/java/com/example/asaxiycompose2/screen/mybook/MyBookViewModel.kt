package com.example.asaxiycompose2.screen.mybook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.model.MyBooksUiData
import com.example.asaxiycompose2.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                    }
                    .launchIn(viewModelScope)
            }
        }
    }
}
