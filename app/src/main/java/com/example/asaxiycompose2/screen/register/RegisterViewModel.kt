package com.example.asaxiycompose2.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiycompose2.data.model.LoginUser
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.menu.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val  repository: LoginRepository,
    private val navigator: AppNavigator
):ViewModel(){
    val errorMessage = MutableSharedFlow<String>()

    fun  onEventDispatcherRegister(intent: RegisterIntent){
        when(intent){
            is RegisterIntent.SetEmailPasswordName -> {
                repository.createUser(intent.email, intent.password, intent.name).onEach {
                    it.onSuccess { uid ->
                        repository.setUser(LoginUser(intent.email, intent.password, intent.name, uid)).launchIn(viewModelScope)
                        navigator.replace(MainScreen())
                    }.onFailure {
                           errorMessage.emit(it.message.toString())
                        }
                }.launchIn(viewModelScope)
            }
        }
    }
}