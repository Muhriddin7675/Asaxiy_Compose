package com.example.asaxiycompose2.screen.audio_info

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.data.model.ProgressData
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme
import com.example.asaxiycompose2.utils.myLog

class AudioInfoScreen(data: BookUIData) : Screen {
    private val bookData = data

    @Composable
    override fun Content() {
        val viewModel = getViewModel<AudioInfoViewModel>()

        viewModel.onEventDispatcher(AudioIntent.HasAudioFromLocal(bookData.bookDocID))
        viewModel.onEventDispatcher(AudioIntent.HasAudioFromBuy(bookData.bookDocID, "audio"))

        val message by viewModel.errorMessage.collectAsState(initial = null)
        if (message != null) {
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        }
        val progressData by viewModel.progress.collectAsState(initial = null)
        val isHasBookLocal by viewModel.isHasBookListener.collectAsState(initial = null)
        val isHasBookBuy by viewModel.isHasBookBuyBtn.collectAsState(initial = null)
        val seekBarVisibility by viewModel.seekBarVisibility.collectAsState(initial = false)

        CustomComposeFunction(
            isHasBookLocal,
            isHasBookBuy,
            progressData,
            seekBarVisibility,
            viewModel::onEventDispatcher
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomComposeFunction(
        isHasBookLocal: Boolean?,
        isHasBookBuy: Boolean?,
        progressData: ProgressData?,
        seekBarVisibility: Boolean,
        onEventDispatcher: (AudioIntent) -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onEventDispatcher(AudioIntent.clickBack)
                        }) {
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
//                    Image(
//                        painter = painterResource(id = R.drawable.dunyoning_ishlari),
//                        contentDescription = null,
//                        Modifier
//                            .size(176.dp, 224.dp)
//                            .padding(top = 20.dp)
//                    )
                    AsyncImage(
                        model = bookData.bookImage,
                        placeholder = painterResource(id = R.drawable.book_app_image),
                        error = painterResource(id = R.drawable.book_app_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(176.dp, 224.dp)
                            .padding(20.dp),
                        contentDescription = null
                    )
                    Text(
                        text = bookData.bookName,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                        color = Color.Black
                    )
                    Text(
                        text = bookData.bookAuthor,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 20.dp),
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        color = Color.Black
                    )
                    if (isHasBookBuy == true) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .height(56.dp)
                                .clickable {
                                    onEventDispatcher.invoke(AudioIntent.AddAudioBuy(bookData.bookName, type = "audio",bookData.bookDocID))
                                }
                                .background(Color(0xFF37417A))

                        ) {
                            Text(
                                text = "Sotib olish",
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .height(56.dp)
                                .clickable {
                                    if (isHasBookLocal == true) {
                                        onEventDispatcher(AudioIntent.GetDownlandAudio(bookData))
                                    } else {
                                        onEventDispatcher(
                                            AudioIntent.ClickDownlandButton(
                                                bookData
                                            )
                                        )
                                    }
                                }
                                .background(Color(0xFF37417A))

                        ) {
                            Text(
                                text = if (isHasBookLocal == true) {
                                    "Audioni Eshitish"
                                } else {
                                    "Yuklab olish"
                                },
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    if (seekBarVisibility) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = progressData!!.progress,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 16.dp, top = 24.dp, bottom = 24.dp),
                                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                                color = Color.Black
                            )
//                            "Porogress" + progressData.seekBar.toString().myLog()
//                            var sliderPosition by remember { mutableStateOf(progressData.seekBar) }

                            Slider(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                value = progressData.seekBar,
                                onValueChange = { newValue ->
                                    progressData.seekBar = newValue
                                },
                                // Disable user interaction with the slider
                            )
                        }
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
                        text = bookData.bookDescription,
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

    @Composable
    fun DefaultPreview() {
        AsaxiyCompose2Theme {
//            CustomComposeFunction()
        }
    }
}