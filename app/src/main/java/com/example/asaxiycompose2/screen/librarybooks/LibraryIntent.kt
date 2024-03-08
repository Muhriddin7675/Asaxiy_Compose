package com.example.asaxiycompose2.screen.librarybooks

import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.data.model.BookUIData

interface LibraryIntent {
    data object GetAllCategoryList:LibraryIntent
    data class ClickAll(val list: AudioDataForAdapter):LibraryIntent
    data class ClickItem(val data: BookUIData):LibraryIntent

}
