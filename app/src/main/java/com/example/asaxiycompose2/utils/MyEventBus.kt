package com.example.asaxiycompose2.utils

import android.database.Cursor
import com.example.asaxiycompose2.data.model.MusicData
import kotlinx.coroutines.flow.MutableStateFlow

object MyEventBus {
    var storagePos: Int = -1
    var roomPos: Int = -1
    var storageCursor: Cursor? = null
    var roomCursor: Cursor? = null

    var totalTime: Int = 0
    var currentTime = MutableStateFlow(0)
    val currentTimeFlow = MutableStateFlow(0)

    var isPlaying = MutableStateFlow(false)
    val currentMusicData = MutableStateFlow<MusicData?>(null)
    var isRepeated = false
    var isRepeatedFlow = MutableStateFlow(false)
}