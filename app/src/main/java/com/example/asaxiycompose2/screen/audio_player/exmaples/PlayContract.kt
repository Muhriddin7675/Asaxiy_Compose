//package com.example.asaxiycompose2.screen.audio_player.exmaples
//
//import com.example.asaxiycompose2.data.model.CommandEnum
//import com.example.asaxiycompose2.data.model.MusicData
//import org.orbitmvi.orbit.ContainerHost
//
//interface PlayContract {
//    interface ViewModel : ContainerHost<UiState, SideEffect> {
//        fun onEventDispatcher(intent: Intent)
//    }
//
//    sealed interface UiState {
//        object InitState : UiState
//        data class CheckMusic(val isSaved: Boolean) : UiState
//    }
//
//    sealed interface SideEffect {
//        data class UserAction(val actionEnum: CommandEnum) : SideEffect
//    }
//
//    sealed interface Intent {
//        data class UserAction(val actionEnum: CommandEnum) : Intent
//        data class SaveMusic(val musicData: MusicData) : Intent
//        data class DeleteMusic(val musicData: MusicData) : Intent
//        data class CheckMusic(val musicData: MusicData) : Intent
//        data class IsRepeated(val isRepeated:Boolean): Intent
//        object Back : Intent
//    }
//
//    interface Directions {
//        suspend fun back()
//    }
//}