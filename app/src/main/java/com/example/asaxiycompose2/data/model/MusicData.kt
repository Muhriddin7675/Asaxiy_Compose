package com.example.asaxiycompose2.data.model

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

data class MusicData(
    val artist: String,
    val title: String,
    val data: String,
    val duration: Long,
    val albumArt: String
)