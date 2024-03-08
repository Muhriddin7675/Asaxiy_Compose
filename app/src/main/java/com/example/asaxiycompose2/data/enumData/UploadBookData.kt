package com.example.asaxiycompose2.data.enumData

import java.io.File

sealed interface UploadBookData {
    data object PAUSE: UploadBookData
    data object RESUME : UploadBookData
    data object CANCEL: UploadBookData

    data class Error(
        val message: String
    ) : UploadBookData

    data class SUCCESS(
        val file: File
    ) : UploadBookData

    data class Progress(
        val uploadBytes: Long,
        val totalBytes: Long
    ) : UploadBookData
}