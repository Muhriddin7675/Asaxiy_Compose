package com.example.asaxiycompose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.asaxiycompose2.navigation.AppNavigationHandler
import com.example.asaxiycompose2.screen.splash.SplashScreen
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var handler: AppNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsaxiyCompose2Theme {
                // A surface container using the 'background' color from the theme
                Navigator(SplashScreen()) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        handler.backStack
                            .onEach { it(navigator) }
                            .launchIn(lifecycleScope)
                    }
                    CurrentScreen()
                }
            }
        }
    }
}
