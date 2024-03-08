package com.example.asaxiycompose2.screen.profile
import androidx.lifecycle.ViewModel
import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiycompose2.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val  repository: LoginRepository,
    private val navigator: AppNavigator
):ViewModel(){
    val errorMessage = MutableSharedFlow<String>()

    fun  onEventDispatcherRegister(intent: ProfileIntent){
        when(intent){
//            is ProfileIntent.SetEmailPasswordName -> {
//                repository.createUser(intent.email, intent.password, intent.name).onEach {
//                    it.onSuccess { uid ->
//                        repository.setUser(LoginUser(intent.email, intent.password, intent.name, uid)).launchIn(viewModelScope)
//                        navigator.replace(MainScreen())
//                    }.onFailure {
//                           errorMessage.emit(it.message.toString())
//                        }
//                }.launchIn(viewModelScope)
//            }
        }
    }
}