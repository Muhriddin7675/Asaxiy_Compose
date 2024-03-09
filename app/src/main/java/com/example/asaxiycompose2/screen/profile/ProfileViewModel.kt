package com.example.asaxiycompose2.screen.profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiycompose2.data.local.pref.MyShar
import com.example.asaxiycompose2.data.model.LoginUser
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.screen.orderhestore.OrderHistoryScreen
import com.google.firestore.v1.StructuredQuery.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val  repository: LoginRepository,
    private val navigator: AppNavigator,

):ViewModel(){
    fun onEventDispatcherProfil(intent: ProfileIntent){
        when(intent){
            ProfileIntent.ClickOrder ->{
                viewModelScope.launch {
                    navigator.navigate(OrderHistoryScreen())
                }
            }
        }
    }
}