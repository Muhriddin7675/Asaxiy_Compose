package com.example.asaxiycompose2.screen.login

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<LoginViewModel>()
        val message by viewModel.errorMessage.collectAsState(initial = null)
        if (message != null) { Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show() }
        LoginContent(eventDispatcher = viewModel::onEventDispatcherLogin)
    }

    @Composable
    fun LoginContent(eventDispatcher: (LoginIntent) -> Unit) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val prevButtonVisible = remember {
            derivedStateOf {
                email.let { it.length > 3 && it.toString().endsWith("@gmail.com") }
                password.let { it.length in 6..8 }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_7),
                    contentDescription = null,
                    Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
            Text(
                text = "Xush kelibsiz!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.nunito_extrabold))
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "E-mailinginzni kiriting",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp),
                fontFamily = FontFamily(Font(R.font.nunito_semi_bold))
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Paronlinginzni kiriting",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp),
                fontFamily = FontFamily(Font(R.font.nunito_semi_bold))
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Button(
                        enabled = prevButtonVisible.value,
                        onClick = {
                             eventDispatcher.invoke(LoginIntent.SetEmailPassword(email, password))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp)
                    )
                    {
                        Text(text = "Kirish")
                    }
                    Button(
                        onClick = {
                         eventDispatcher.invoke(LoginIntent.OpenRegisterScreen)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp)
                            .shadow(elevation = 100.dp),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    )
                    {
                        Text(
                            text = "Email orqali  ro'yxatdan o'tish",
                            color = Color.Blue
                        )
                    }
                }
            }

        }

    }

    @Preview
    @Composable
    fun PreviewLoginContent() {
        AsaxiyCompose2Theme {
            LoginContent({})
        }
    }


}