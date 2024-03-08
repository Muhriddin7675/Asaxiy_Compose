package com.example.asaxiycompose2.data.enumData

import java.io.File

sealed interface UploadData {
    data object PAUSE: UploadData
    data object RESUME : UploadData
    data object CANCEL: UploadData

    data class Error(
        val message: String
    ) : UploadData

    data class SUCCESS(
        val file: File
    ) : UploadData

    data class Progress(
        val uploadBytes: Long,
        val totalBytes: Long
    ) : UploadData
}