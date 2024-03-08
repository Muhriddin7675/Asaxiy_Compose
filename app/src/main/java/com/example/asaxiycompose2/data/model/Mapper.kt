package com.example.asaxiycompose2.data.model

import com.example.asaxiycompose2.data.local.room.entity.BookEntity

object Mapper {
    fun BookEntity.toMyBooksUiData(): MyBooksUiData =
        MyBooksUiData(
            id = this.id,
            docId = this.docId,
            bookName = this.bookName,
            bookAuthor = this.bookAuthor,
            imagePath = this.imagePath,
            bookPath = this.bookPath,
            description = this.description,
            bookSize = this.bookSize,
            status = if(this.type == 0) StatusEnum.PDF else StatusEnum.AUDIO
    )
}