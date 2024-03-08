package com.example.asaxiycompose2.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File
@Parcelize
data class AudioPlayerData(
    val bookUIData: BookUIData,
    val file: File
):Parcelable