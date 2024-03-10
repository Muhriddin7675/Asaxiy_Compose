//package com.example.asaxiycompose2.screen.audio_player.exmaples
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.asaxiybooks.domain.AppRepository
//import com.example.asaxiycompose2.data.model.CommandEnum
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import org.orbitmvi.orbit.syntax.simple.intent
//import org.orbitmvi.orbit.syntax.simple.postSideEffect
//import org.orbitmvi.orbit.viewmodel.container
//import javax.inject.Inject
//
//@HiltViewModel
//class PlayViewModel @Inject constructor(
//    private val directions: PlayContract.Directions
//) : PlayContract.ViewModel, ViewModel() {
//    override val container =
//        container<PlayContract.UiState, PlayContract.SideEffect>(PlayContract.UiState.InitState)
//
//    override fun onEventDispatcher(intent: PlayContract.Intent) {
//
//
//        when (intent) {
//            PlayContract.Intent.Back -> {
//                viewModelScope.launch {
//                    directions.back()
//                }
//            }
//
//            is PlayContract.Intent.CheckMusic -> {
////                intent { reduce { PlayContract.UiState.CheckMusic(repository.checkSavedMusic(intent.musicData)) } }
//            }
//
//            is PlayContract.Intent.UserAction -> {
//                intent {
//                    postSideEffect(PlayContract.SideEffect.UserAction(intent.actionEnum))
//                }
//            }
//
//            is PlayContract.Intent.DeleteMusic -> {
////                repository.removeFromFavourite(intent.musicData.toEntity())
//            }
//
//            is PlayContract.Intent.SaveMusic -> {
////                repository.addToFavourite(intent.musicData.toEntity())
//            }
//
//            is PlayContract.Intent.IsRepeated -> {
//                intent { postSideEffect(PlayContract.SideEffect.UserAction(CommandEnum.IS_REPEATED)) }
//            }
//        }
//    }
//}