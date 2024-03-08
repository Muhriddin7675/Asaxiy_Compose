package com.example.asaxiycompose2.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.KeyEventDispatcher
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<SplashViewModel>()
        viewModel.onEventDispatcher(SplashIntent.Intro)
        SplashContent()
    }

}

@Composable
private fun SplashContent() {
   Box(modifier = Modifier.fillMaxSize()){
       Image(painter = painterResource(id = R.drawable.ic_asaxiy_logo),
           contentDescription = null,
           modifier = Modifier.size(200.dp,200.dp)
               .align(Alignment.Center))
   }
}

@Preview
@Composable
private fun PreviewSplashContent() {
    AsaxiyCompose2Theme {
        SplashContent()
    }
}