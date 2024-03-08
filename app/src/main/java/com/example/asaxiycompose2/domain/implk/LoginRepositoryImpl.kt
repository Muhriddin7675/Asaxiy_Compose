package com.example.asaxiybooks.domain.implk

import android.annotation.SuppressLint
import com.example.asaxiycompose2.data.local.pref.MyShar
import com.example.asaxiycompose2.data.model.LoginUser
import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiycompose2.utils.myLog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginRepositoryImpl @Inject constructor(private val myShar: MyShar) : LoginRepository {
    private var auth = Firebase.auth
    private var data = Firebase.firestore
    var name = ""


    override fun addUserLogin(email: String, password: String): Flow<Boolean> = callbackFlow {

            auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    // Kirish muvaffaqiyatli amalga oshirildi
                    val currentUser = auth.currentUser
                    //currentUser?.uid
                    myShar.userNumber(1)
                    currentUser?.let {
                        myShar.setToken(currentUser.uid)
                    }
                    "My shared user login" + myShar.getToken().myLog()
                    trySend(true)
                } else {
                    trySend(false)
                }
            }
        awaitClose()
    }

    @SuppressLint("LogNotTimber")
    override fun setUser(data1: LoginUser): Flow<Unit> = callbackFlow {
        data.collection("users")
            .document()
            .set(data1)
            .addOnSuccessListener {
                "set User sucses ".myLog()

                //Log.d("PPP", it.toString())
            }
            .addOnFailureListener {
                "set User exeption ${it.message} ".myLog()

            }
        awaitClose()

    }

    @SuppressLint("LogNotTimber")
    override fun createUser(email: String, password: String, name: String): Flow<Result<String>> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.addOnCompleteListener {
                            val user = auth.currentUser
                            user?.let {
                                myShar.setToken(it.uid)
                                myShar.getToken().myLog()
                                trySend(Result.success(it.uid))
                            }

                        }
                    }
                }
                .addOnFailureListener {
                    it.message?.myLog()
                    trySend(Result.failure(it))
                }
            awaitClose()
        }
}