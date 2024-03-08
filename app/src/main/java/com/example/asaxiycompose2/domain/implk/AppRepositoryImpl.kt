package com.example.asaxiycompose2.domain.implk

import com.example.asaxiycompose2.data.enumData.UploadBookData
import com.example.asaxiycompose2.data.enumData.UploadData
import com.example.asaxiycompose2.data.local.pref.MyShar
import com.example.asaxiycompose2.data.local.room.dao.BooksDao
import com.example.asaxiycompose2.data.local.room.entity.BookEntity
import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiybooks.data.model.BuyBook
import com.example.asaxiycompose2.data.model.BuyBookType
import com.example.asaxiycompose2.data.model.Mapper.toMyBooksUiData
import com.example.asaxiycompose2.data.model.MyBooksUiData
import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.utils.Mapper.toAudioDataList
import com.example.asaxiycompose2.utils.Mapper.toAudioDataListByAbdulbosit
import com.example.asaxiycompose2.utils.myLog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val myShared: MyShar,
    private val booksDao: BooksDao
) : AppRepository {
    private val fireStore = Firebase.firestore
    private val fireStorage = Firebase.storage
    private var downloadTask: FileDownloadTask? = null
    private var bookDownloadTask: FileDownloadTask? = null

    override fun setHasIntroPage(number: Int) = myShared.setHasIntroPage(number)
    override fun getHasIntroPage(): Int = myShared.getHasIntroPage()
    override fun getAudioBooks(): Flow<Result<List<BookUIData>>> = callbackFlow {
        fireStore
            .collection("booksTable")
            .whereEqualTo("type", "AUDIO")
            .get()
            .addOnSuccessListener {
                val list = it.toAudioDataList()
                trySend(Result.success(list))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getAllAudioCategory(): Flow<Result<List<Pair<String, String>>>> =
        callbackFlow {
            val data = ArrayList<Pair<String, String>>()
            fireStore.collection("category")
                .get().addOnSuccessListener {
                    var size = it.size()
                    it.forEach { snapshot ->
                        size--
                        val categoryId = snapshot.id
                        val categoryName =
                            snapshot.data.getOrDefault("categoryName", "Ali").toString()
                        data.add(Pair(categoryId, categoryName))
                        if (size == 0) {
                            trySend(Result.success(data))
                        }
                    }
                }
            awaitClose()
        }

    override fun getAudioBooks(categoryId: String): Flow<Result<List<BookUIData>>> = callbackFlow {
        "Repos Get Audios".myLog()
        fireStore
            .collection("audio")
            .whereEqualTo("audioCategory", categoryId)
            .get()
            .addOnSuccessListener {
                "repo getAudio Succes".myLog()
                val list = it.toAudioDataListByAbdulbosit()
                trySend(Result.success(list))
            }.addOnFailureListener {
                "repo getAudio Failure".myLog()
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getPdFBooks(categoryId: String): Flow<Result<List<BookUIData>>> = callbackFlow {
        "Repos Get Audios".myLog()
        fireStore
            .collection("booksTable")
            .whereEqualTo("cotegory", categoryId)
            .get()
            .addOnSuccessListener {
                "repo getAudio Succes".myLog()
                val list = it.toAudioDataList()
                trySend(Result.success(list))
            }.addOnFailureListener {
                "repo getAudio Failure".myLog()
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getAllBooksFromRoom(): Flow<List<MyBooksUiData>> = callbackFlow {
        val ls = booksDao.getAllBooksFromLocal()
        if (ls.isNotEmpty()) {
            val list = ArrayList<MyBooksUiData>()
            ls.forEach {
                list.add(it.toMyBooksUiData())
            }
            trySend(list)
        } else {
            val emptyList = ArrayList<MyBooksUiData>()
            trySend(emptyList)
        }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun loadCategories(): Flow<Result<List<AudioDataForAdapter>>> = callbackFlow {

        fireStore.collection("audio")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val map = HashMap<String, ArrayList<BookUIData>>()
                val ls = arrayListOf<AudioDataForAdapter>()

                querySnapshot.forEach {

                    val category = it.data["audioCategory"].toString()

                    if (!map.containsKey(category)) {
                        map[category] = arrayListOf()
                    }
                    val data = BookUIData(
                        bookDocID = it.id,
                        bookAuthor = it.data.getOrDefault("audioAuthor", "").toString(),
                        category = it.data.getOrDefault("audioCategory", "").toString(),
                        bookDescription = it.data.getOrDefault("audioDescription", "").toString(),
                        bookImage = it.data.getOrDefault("audioImage", "").toString(),
                        bookName = it.data.getOrDefault("audioName", "").toString(),
                        bookPath = it.data.getOrDefault("audioPath", "").toString(),
                        bookSize = it.data.getOrDefault("audioSize", "0").toString(),
                        type = "AUDIO",
                        categoryName = ""
                    )

                    map[category]!!.add(data)
                }

                map.keys.forEach {
                    val list = map[it]
                    ls.add(AudioDataForAdapter(it, "", list!!))
                }

                trySend(Result.success(ls))
                close()
            }
            .addOnFailureListener { error ->
                trySend(Result.failure(error))
                close()
            }

        awaitClose()
    }

    override fun loadCategoriesBooks(): Flow<Result<List<AudioDataForAdapter>>> = callbackFlow {

        fireStore.collection("booksTable")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val map = HashMap<String, ArrayList<BookUIData>>()
                val ls = arrayListOf<AudioDataForAdapter>()

                querySnapshot.forEach {
                    val category = it.data["cotegory"].toString()
                    if (!map.containsKey(category)) {
                        map[category] = arrayListOf()
                    }
                    val data = BookUIData(
                        bookName = it.data["bookName"].toString(),
                        bookAuthor = it.data["bookAuthor"].toString(),
                        bookPath = it.data["bookPath"].toString(),
                        bookDescription = it.data["description"].toString(),
                        bookImage = it.data["imagePath"].toString(),
                        bookSize = it.data["size"].toString(),
                        category = it.data["category"].toString(),
                        type = it.data["PDF"].toString(),
                        bookDocID = it.id,
                        categoryName = it.data["categoryName"].toString()
                    )
                    map[category]!!.add(data)
                }

                map.keys.forEach {
                    val list = map[it]
                    ls.add(AudioDataForAdapter(it, "", list!!))
                }

                trySend(Result.success(ls))
                close()
            }
            .addOnFailureListener { error ->
                trySend(Result.failure(error))
                close()
            }

        awaitClose()
    }


    override fun getAudioInCategory(
        categoryid: String,
        categoryName: String
    ): Flow<ArrayList<AudioDataForAdapter>> = callbackFlow {
        val productData = ArrayList<BookUIData>()
        var index1 = 0
        var index2 = 0
        fireStore.collection("category")
            .document(categoryid)
            .get()
            .addOnSuccessListener {
                fireStore.collection("audio")
                    .whereEqualTo("audioCategory", it.id)
                    .get().addOnSuccessListener { querySnapshot ->
                        val size = querySnapshot.size()
                        querySnapshot.forEach { queryDocumentSnapshot ->
                            val prod = queryDocumentSnapshot.id
                            productData.add(
                                BookUIData(prod, "", "", "", "", "", 0.toString(), "", "", "")
                            )
                            fireStore.collection("audio")
                                .document(prod)
                                .get().addOnSuccessListener { documentSnapshot ->
                                    val audioUi =
                                        index1++
                                    fireStore.collection("users")
//                                        .document(sellerId)
//                                        .get().addOnSuccessListener {
//                                            productData[index2].sellerName =
//                                                it.data?.getOrDefault("name", "Ali").toString()
//                                            index2++
//                                            if (index2 == size) {
//                                                trySend(productData)
//                                            }
//                                        }
                                }
                                .addOnFailureListener {

                                }
                        }
                    }
            }

        awaitClose()
    }

    override fun hasBookFromLocal(docID: String): Flow<Result<Boolean>> = callbackFlow {
        val result = booksDao.isHasBook(docID)
        trySend(Result.success(result))
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun downloadBook(data: BookUIData): Flow<UploadData> = callbackFlow {
        val book = File.createTempFile("Audios", ".mp3")
        data.bookPath.myLog()

        downloadTask = fireStorage.getReferenceFromUrl(data.bookPath).getFile(book)

        downloadTask!!.addOnSuccessListener {
            booksDao.insertBooks(
                BookEntity(
                    0,
                    data.bookDocID,
                    data.bookName,
                    data.bookAuthor,
                    data.bookDescription,
                    data.bookImage,
                    book.absolutePath,
                    data.bookSize,
                    data.category,
                    1
                )
            )
            trySend(UploadData.SUCCESS(book))
        }
            .addOnFailureListener {
                "repo mp3 download fail:${it.message}".myLog()
                trySend(UploadData.Error(it.message.toString()))
            }
            .addOnProgressListener {
                trySend(UploadData.Progress(it.bytesTransferred, it.totalByteCount))
            }
            .addOnPausedListener {
                trySend(UploadData.PAUSE)
            }
            .addOnCanceledListener {
                trySend(UploadData.CANCEL)
            }
        awaitClose()
    }
        .flowOn(Dispatchers.IO)

    override fun resumeUploading() {
        downloadTask?.resume()
    }

    override fun pauseUploading() {
        downloadTask?.pause()
    }

    override fun cancelUploading() {
        downloadTask?.cancel()
    }

    override fun getDownloadedBook(data: BookUIData): Flow<File> = callbackFlow {
        val bookLocalPath = booksDao.getBooksByDocID(data.bookDocID).bookPath
        val file = File(bookLocalPath)
        trySend(file)
        awaitClose()
    }

    override fun downloadBookPdf(data: BookUIData): Flow<UploadBookData> = callbackFlow {
        val book = File.createTempFile("Pdf", ".pdf")
        data.bookPath.myLog()
            bookDownloadTask = fireStorage
            .getReferenceFromUrl(data.bookPath)
            .getFile(book)

        bookDownloadTask!!.addOnSuccessListener {
            booksDao.insertBooks(
                BookEntity(0,
                    data.bookDocID,
                    data.bookName,
                    data.bookAuthor,
                    data.bookDescription,
                    data.bookImage,
                    book.absolutePath,
                    data.bookSize,
                    data.category,
                    0
                )
            )
            trySend(UploadBookData.SUCCESS(File(book.absolutePath)))
        }
            .addOnFailureListener {
                trySend(UploadBookData.Error(it.message.toString()))
            }
            .addOnProgressListener {
                trySend(UploadBookData.Progress(it.bytesTransferred, it.totalByteCount))
            }
            .addOnPausedListener {
                trySend(UploadBookData.PAUSE)
            }
            .addOnCanceledListener {
                trySend(UploadBookData.CANCEL)
            }
        awaitClose()
    }
        .flowOn(Dispatchers.IO)

    override fun getDownloadedBookPdf(data: BookUIData): Flow<File> = callbackFlow {
        val bookLocalPath = booksDao.getBooksByDocID(data.bookDocID).bookPath
        val file = File(bookLocalPath)
        trySend(file)
        awaitClose()
    }

    override fun pauseBookUploading() {
        bookDownloadTask?.pause()
    }

    override fun cancelBookUploading() {
        bookDownloadTask?.cancel()
    }

    override fun resumeBookUploading() {
        bookDownloadTask?.resume()
    }

    override fun hasBookFromLocalPdf(docID: String): Flow<Result<Boolean>> = callbackFlow {
        val result = booksDao.isHasBook(docID)
        trySend(Result.success(result))
        awaitClose()
    }.flowOn(Dispatchers.IO)


    override fun getBooksBuy(bookDocId: String, type: String): Flow<Result<Boolean>> =

        callbackFlow {
            "get Books by " + myShared.getToken().myLog()
            fireStore.collection("buyBook")
                .whereEqualTo("userId", myShared.getToken())
                .whereEqualTo("bookDocId", bookDocId)
                .whereEqualTo("type", type)
                .addSnapshotListener { value, error ->
                    "value " + value.toString().myLog()
                    if (value == null || value.isEmpty) {
                        trySend(Result.success(true))
                    }
                    if (value != null && !value.isEmpty) {
                        trySend(Result.success(false))
                    } else {
                        if (error != null) {
                            trySend(Result.failure(error))
                        } else {
                            trySend(Result.failure(Exception("Unknown error!")))
                        }
                    }

                }
            awaitClose()
        }

    override fun addBookBuy(bookName: String, type: String, bookDocId: String): Flow<Result<Unit>> =
        callbackFlow {
            "add book by  " + myShared.getToken().myLog()
            fireStore.collection("buyBook")
                .document(System.currentTimeMillis().toString())
                .set(
                    BuyBook(
                        bookDocId = bookDocId,
                        bookName = bookName,
                        userId = myShared.getToken(),
                        type = type
                    )
                )
                .addOnSuccessListener {
                    trySend(Result.success(Unit))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
            awaitClose()
        }

    override fun getByBookIdList(): Flow<Result<List<BuyBookType>>> = callbackFlow {
        "get by book list" + myShared.getToken().myLog()
        fireStore.collection("buyBook")
            .whereEqualTo("userId", myShared.getToken())
            .addSnapshotListener { value, error ->
                val list = ArrayList<BuyBookType>()
                if (value != null) {
                    value.forEach {
                        list.add(
                            BuyBookType(
                                docId = it.data.getOrDefault("bookDocId", "MMMMMMMM").toString(),
                                type = it.data.getOrDefault("type", "MMMMM").toString()
                            )
                        )
                    }
                    trySend(Result.success(list))
                } else {
                    if (error != null) {
                        trySend(Result.failure(error))
                    } else {
                        trySend(Result.failure(Exception("Unknown error !")))
                    }
                }
            }
        awaitClose()
    }

    override fun getBuyBookList(bookDocIdList: List<BuyBookType>): Flow<Result<List<BookUIData>>> =
        callbackFlow {
            val bookList = ArrayList<BookUIData>()
            val size = bookDocIdList.size
            var index = 0;
            bookDocIdList.forEach {
                if (it.type == "pdf") {
                    fireStore.collection("booksTable")
                        .document(it.docId)
                        .addSnapshotListener { value, error ->
                            if (value != null) {
                                bookList.add(
                                    BookUIData(
                                        bookName = value.data!!["bookName"].toString(),
                                        bookAuthor = value.data!!["bookAuthor"].toString(),
                                        bookPath = value.data!!["bookPath"].toString(),
                                        bookDescription = value.data!!["description"].toString(),
                                        bookImage = value.data!!["imagePath"].toString(),
                                        bookSize = value.data!!["size"].toString(),
                                        category = value.data!!["category"].toString(),
                                        type = "PDF",
                                        bookDocID = value.id,
                                        categoryName = value.data!!["categoryName"].toString()
                                    )
                                )
                                index++

                                if (index == size) {
                                    trySend(Result.success(bookList))
                                }
                            } else {
                                if (error != null) {
                                    trySend(Result.failure(error))
                                } else {
                                    trySend(Result.failure(Exception("Unknown error !")))
                                }
                            }

                        }
                }
                else{
                    fireStore.collection("audio")
                        .document(it.docId)
                        .addSnapshotListener { value, error ->
                            if (value != null) {
                                bookList.add(
                                    BookUIData(
                                        bookDocID = value.id,
                                        bookAuthor = value.data!!.getOrDefault("audioAuthor", "").toString(),
                                        category = value.data!!.getOrDefault("audioCategory", "").toString(),
                                        bookDescription = value.data!!.getOrDefault("audioDescription", "").toString(),
                                        bookImage = value.data!!.getOrDefault("audioImage", "").toString(),
                                        bookName = value.data!!.getOrDefault("audioName", "").toString(),
                                        bookPath = value.data!!.getOrDefault("audioPath", "").toString(),
                                        bookSize = value.data!!.getOrDefault("audioSize", "0").toString(),
                                        type = "AUDIO",
                                        categoryName = ""
                                    )
                                )
                                index++

                                if (index == size) {
                                    trySend(Result.success(bookList))
                                }
                            } else {
                                if (error != null) {
                                    trySend(Result.failure(error))
                                } else {
                                    trySend(Result.failure(Exception("Unknown error !")))
                                }
                            }

                        }
                }
            }

            awaitClose()
        }


//    override fun login(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val user = auth.currentUser
//                val userUid = user?.uid
//                pref.setToken(userUid.toString())
//                trySend(Result.success(Unit))
//            } else {
//                trySend(Result.failure(Exception("Login")))
//            }
//        }
//        awaitClose()
//    }
//
//    override fun register(userData: UserData, password: String): Flow<Result<Unit>> = callbackFlow {
//        auth.createUserWithEmailAndPassword(userData.gmail, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val user = auth.currentUser
//                val userUid = user?.uid
//                pref.setToken(userUid.toString())
//                fireStore.collection("users")
//                    .add(UserRequest(userData.firstName, userData.lastName, userData.phone, userData.gmail, userUid.toString()))
//                    .addOnSuccessListener {
//                        trySend(Result.success(Unit))
//                    }
//                    .addOnFailureListener {
//                        trySend(Result.failure(it))
//                    }
//            } else {
//                trySend(Result.failure(Exception("Login")))
//            }
//        }
//        awaitClose()
//    }

    override fun bookHasBuyBooks(docID: String): Flow<Result<Boolean>> = callbackFlow {
        fireStore
            .collection("buyBook")
            .whereEqualTo("bookDocId", docID)
            .whereEqualTo("type", "audio")
            .get()
            .addOnSuccessListener {
                "Kitob Mavjudmi ${it.isEmpty}"
                trySend(Result.success(it.isEmpty))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }


}