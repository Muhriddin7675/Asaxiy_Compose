package com.example.asaxiycompose2.screen.librarybooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel() {
    val loadCategoryBookList = MutableSharedFlow<List<AudioDataForAdapter>>()
    val errorMessage = MutableSharedFlow<String>()
    val progress = MutableSharedFlow<Boolean>()

    fun onEventDispatcherLibrary(intent: LibraryIntent) {
        when (intent) {
            LibraryIntent.GetAllCategoryList -> {
                progress.tryEmit(true)
                repository.loadCategoriesBooks().onEach { result ->
                    result.onSuccess {
                        progress.emit(false)
                        loadCategoryBookList.emit(it)
                    }
                    result.onFailure {
                        progress.emit(false)
                        errorMessage.emit(it.message.toString())
                    }
                }.launchIn(viewModelScope)

            }
        }
    }
}