package com.example.asaxiycompose2.screen.audio_info

import com.example.asaxiycompose2.data.model.BookUIData

interface AudioIntent {
    data object clickBack : AudioIntent
    data class ClickDownlandButton(val data:BookUIData) : AudioIntent

    data class HasAudioFromLocal(val bookId: String) : AudioIntent
    data class HasAudioFromBuy(val bookId: String, val type: String) : AudioIntent
    data class GetDownlandAudio(val data: BookUIData):AudioIntent
    data class AddAudioBuy(val bookName: String, val type: String, val bookDocId: String):AudioIntent

}