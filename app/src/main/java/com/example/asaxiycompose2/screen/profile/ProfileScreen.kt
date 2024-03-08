package com.example.asaxiycompose2.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme


class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<ProfileViewModel>()
        ProfileContent(eventDispatcher = viewModel::onEventDispatcherRegister)
    }

    @Composable
    fun ProfileContent(eventDispatcher: (ProfileIntent) -> Unit) {
        val email by remember { mutableStateOf("") }
        val password by remember { mutableStateOf("") }
        val name by remember { mutableStateOf("") }

        val prevButtonVisible = remember {
            derivedStateOf {
                email.let { it.length > 3 && it.endsWith("@gmail.com") }
                password.length in 6..8
                name.length >= 3
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(
                text = "Profile",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                modifier = Modifier.align(Alignment.Start).fillMaxWidth()
            )
        }

    }
    @Preview
    @Composable
    fun PreviewProfileContent() {
        AsaxiyCompose2Theme {
            ProfileContent({})
        }
    }
}