package com.example.asaxiycompose2.screen.book_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.data.enumData.UploadBookData
import com.example.asaxiycompose2.data.model.ProgressData
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.book_info.BookIntent.AddBookBuy
import com.example.asaxiycompose2.utils.myLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.pdf.PdfScreen
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel() {

    val isHasBookListener = MutableSharedFlow<Boolean>()
    val errorMessage = MutableSharedFlow<String>()
    val isHasBookBuyBtn = MutableSharedFlow<Boolean>()
    val progress = MutableSharedFlow<ProgressData>()
    val seekBarVisibility = MutableStateFlow(false)
    fun onEventDispatcher(intent: BookIntent) {
        when (intent) {
            BookIntent.clickBack -> {
                viewModelScope.launch {
                    navigator.back()
                }
            }

            is BookIntent.ClickDownlandButton -> {
                val data = intent.data
                repository.downloadBookPdf(data).onEach {
                 seekBarVisibility.emit(true)
                    when (it) {
                        UploadBookData.CANCEL -> {
                            errorMessage.emit("CANCEL")
                        }

                        UploadBookData.PAUSE -> {
                            errorMessage.emit("PAUSE")
                        }

                        UploadBookData.RESUME -> {
                            errorMessage.emit("RESUME")
                        }

                        is UploadBookData.Error -> {
                            errorMessage.emit(it.message)
                        }

                        is UploadBookData.Progress -> {
                            val uploadBytes = it.uploadBytes / 1024
                            val totalBytes = it.totalBytes / 1024

                            "${uploadBytes}/${totalBytes} KB   ${it.uploadBytes * 100 / it.totalBytes}%".myLog()
                            "${it.uploadBytes * 100 / it.totalBytes}".myLog()
                            progress.emit(
                                ProgressData(
                                    progress = "${uploadBytes}/${totalBytes} KB   ${it.uploadBytes * 100 / it.totalBytes}%",
                                    seekBar = (it.uploadBytes  / it.totalBytes).toFloat()
                                )
                            )
                        }

                        is UploadBookData.SUCCESS -> {
                            errorMessage.emit("Success")
                            onEventDispatcher(
                                BookIntent.AddBookBuy(
                                    data.bookName,
                                    "pdf",
                                    data.bookDocID
                                )
                            )
                            seekBarVisibility.emit(false)
                            onEventDispatcher(BookIntent.HasBookFromLocal(data.bookDocID))
                            onEventDispatcher(BookIntent.HasBookFromBuy(data.bookDocID, "pdf"))
                        }
                    }
                }.launchIn(viewModelScope)
            }

            is AddBookBuy -> {
                val bookName = intent.bookName
                val type = intent.type
                val bookDocId = intent.bookDocId
                repository.addBookBuy(bookName, type, bookDocId)
                    .onEach { result ->
                        result.onSuccess {
                            errorMessage.emit("You bought the book !")
                        }
                        result.onFailure {
                            errorMessage.emit(it.message.toString())
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is BookIntent.HasBookFromBuy -> {
                val bookDocId = intent.bookId
                val type = intent.type
                "getBookBuy $bookDocId".myLog()
                repository.getBooksBuy(bookDocId, type)
                    .onEach { result ->
                        result.onSuccess {
                            "getBookBuySucses $it".myLog()
                            isHasBookBuyBtn.emit(it)
                        }
                        result.onFailure {
                            errorMessage.emit(it.message.toString())
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is BookIntent.HasBookFromLocal -> {
                val docID = intent.bookId
                repository.hasBookFromLocalPdf(docID)
                    .onEach { result ->

                        result.onSuccess {
                            "Book Has local method result : $it".myLog()
                            isHasBookListener.emit(it)
                        }
                        result.onFailure {

                            errorMessage.emit(it.message.toString())
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is BookIntent.GetDownlandBook ->{
                val data = intent.data
                repository.getDownloadedBookPdf(data).onEach {
                    navigator.navigate(PdfScreen(it.path))
                }.launchIn(viewModelScope)
            }
        }
    }
}