package com.example.asaxiycompose2.screen.book_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme

class BookInfoScreen : Screen {
    @Composable
    override fun Content() {
        CustomComposeFunction()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomComposeFunction() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.MoreVert, "backIcon")
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.White,
                    ),
                )
            }, content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dunyoning_ishlari),
                        contentDescription = null,
                        Modifier
                            .size(176.dp, 224.dp)
                            .padding(top = 20.dp)
                    )
                    Text(
                        text = "Olam va odam",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                        color = Color.Black
                    )
                    Text(
                        text = "Ilm va kashfityotlar",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 20.dp),
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .height(56.dp)
                            .background(Color(0xFF008dff))

                    ) {
                        Text(
                            text = "Sotib olish",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Text(
                        text = "Kitob Haqida",
                        fontSize = 22.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 24.dp),
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        color = Color.Black
                    )

                    Text(
                        text = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before the final copy is available.",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 24.dp, end = 16.dp),
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        color = Color(0xFFA7A7A7)
                    )
                }

            })
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AsaxiyCompose2Theme {
            CustomComposeFunction()
        }
    }
}