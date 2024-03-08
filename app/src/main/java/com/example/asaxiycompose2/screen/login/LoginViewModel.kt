package com.example.asaxiycompose2.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.login.LoginIntent.SetEmailPassword
import com.example.asaxiycompose2.screen.menu.MainScreen
import com.example.asaxiycompose2.screen.register.RegisterScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val repository: LoginRepository
):ViewModel() {
     val errorMessage = MutableSharedFlow<String>()

    fun  onEventDispatcherLogin(intent: LoginIntent){
        when(intent){
           is SetEmailPassword -> {
               repository.addUserLogin(intent.email,intent.password).onEach {
                   if(it){
                       navigator.replace(MainScreen())
                   }else{
                     errorMessage.emit("Siz oldin ro'yxatdan o'ting !")
                   }
               }.launchIn(viewModelScope)
           }
           LoginIntent.OpenRegisterScreen ->{
               viewModelScope.launch {
                   navigator.navigate(RegisterScreen())
               }
           }
        }
    }

}