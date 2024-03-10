package com.example.asaxiycompose2.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiycompose2.data.local.pref.MyShar
import com.example.asaxiycompose2.data.local.room.dao.BooksDao
import com.example.asaxiycompose2.data.model.LoginUser
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.login.LoginScreen
import com.example.asaxiycompose2.screen.orderhestore.OrderHistoryScreen
import com.example.asaxiycompose2.screen.orderhestore.OrderIntent
import com.google.firestore.v1.StructuredQuery.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val navigator: AppNavigator,
    private val myShar: MyShar,
    private val dao: BooksDao

) : ViewModel() {
    fun onEventDispatcherProfil(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.ClickOrder -> {
                viewModelScope.launch {
                    navigator.navigate(OrderHistoryScreen())
                }
            }
            ProfileIntent.ClickLogAut -> {
                viewModelScope.launch {
                    myShar.userNumber(0)
                    dao.nukeTable()
                    navigator.replace(LoginScreen())
                }
            }
        }
    }
}