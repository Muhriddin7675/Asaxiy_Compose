package com.example.asaxiycompose2.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.screen.audio_player.PlayScreen
import com.example.asaxiycompose2.screen.audiobook.AudioScreen
import com.example.asaxiycompose2.screen.librarybooks.LibraryScreen
import com.example.asaxiycompose2.screen.mybook.MyBookScreen
import com.example.asaxiycompose2.screen.profile.ProfileScreen
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme


class MainScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<MainViewModel>()
        MainContent(eventDispatcher = viewModel::onEventDispatcher)
    }
}

@Composable
private fun MainContent(eventDispatcher: (MainIntent) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabNavigator(HomeTab) {
            Scaffold(
                content = { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(BooksTab)
                        TabNavigationItem(AudioTab)
                        TabNavigationItem(ProfileTab)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewContent() {
    AsaxiyCompose2Theme {
        MainContent({})
    }
}

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Kitoblarim"
            val icon = painterResource(R.drawable.ic_menu_book)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
          MyBookScreen().Content()
        }
    }
}
object BooksTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Kutubxona"
            val icon = painterResource(R.drawable.ic_menu_library)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
       LibraryScreen().Content()
        }
    }
}
object AudioTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Audion"
            val icon = painterResource(R.drawable.ic_menu_audio)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
           AudioScreen().Content()
        }
    }
}
object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profil"
            val icon = painterResource(R.drawable.ic_menu_profile)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            PlayScreen().Content()
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}