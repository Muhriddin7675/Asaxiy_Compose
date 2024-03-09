package com.example.asaxiybooks.domain

import com.example.asaxiycompose2.data.enumData.UploadBookData
import com.example.asaxiycompose2.data.enumData.UploadData
import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.data.model.BuyBookType
import com.example.asaxiycompose2.data.model.MyBooksUiData
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AppRepository {
    fun setHasIntroPage(number:Int)
    fun getHasIntroPage():Int
    fun getAudioBooks() : Flow<Result<List<BookUIData>>>

    fun getAllBooksFromRoom():Flow<List<MyBooksUiData>>

    fun getPdFBooks(categoryId: String): Flow<Result<List<BookUIData>>>
    fun getAllAudioCategory() :Flow<Result<List<Pair<String,String>>>>
    fun getAudioBooks(categoryId: String) : Flow<Result<List<BookUIData>>>

    fun loadCategories(): Flow<Result<List<AudioDataForAdapter>>>

    fun loadCategoriesBooks():Flow<Result<List<AudioDataForAdapter>>>
    fun getAudioInCategory(categoryid: String, categoryName: String): Flow<ArrayList<AudioDataForAdapter>>
    fun hasBookFromLocal(docID: String): Flow<Result<Boolean>>
    fun downloadBook(data: BookUIData): Flow<UploadData>
    fun getDownloadedBook(data: BookUIData): Flow<File>
    fun bookHasBuyBooks(docID:String) : Flow<Result<Boolean>>



    fun getDownloadedBookPdf(data: BookUIData): Flow<File>
    fun downloadBookPdf(data: BookUIData): Flow<UploadBookData>
    fun hasBookFromLocalPdf(docID: String): Flow<Result<Boolean>>
    fun getBooksBuy(bookDocId:String,type: String):Flow<Result<Boolean>>
    fun addBookBuy(bookName: String,type:String,bookDocId:String):Flow<Result<Unit>>
    fun getByBookIdList():Flow<Result<List<BuyBookType>>>



    fun pauseUploading()
    fun cancelUploading()
    fun resumeUploading()

    fun pauseBookUploading()
    fun cancelBookUploading()
    fun resumeBookUploading()
    fun getBuyBookList(bookDocIdList: List<BuyBookType>):Flow<Result<List<BookUIData>>>
}