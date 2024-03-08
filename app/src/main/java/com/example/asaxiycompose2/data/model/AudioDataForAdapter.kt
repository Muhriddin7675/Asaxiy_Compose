package com.example.asaxiycompose2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioDataForAdapter(
    val id: String,
    val categoryName: String,
    val list: List<BookUIData>
) : Parcelable