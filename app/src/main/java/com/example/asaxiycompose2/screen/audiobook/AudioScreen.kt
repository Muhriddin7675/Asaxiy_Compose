package com.example.asaxiycompose2.screen.audiobook

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme

class AudioScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<AudioViewModel>()

        viewModel.onEventDispatcherAudio(AudioIntent.GetAllCategoryList)

        val categoryList by viewModel.loadCategoryBookList.collectAsState(initial = null)
        val progress = viewModel.progress.collectAsState()
        val message by viewModel.errorMessage.collectAsState(initial = null)
        if (message != null) {
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        }
        if (categoryList == null || categoryList!!.isEmpty()) {
            AudioContent(emptyList(), viewModel, progress)
        } else {
            AudioContent(categoryList!!, viewModel, progress)
        }
    }

}


@Composable
fun AudioContent(
    list: List<AudioDataForAdapter>,
    viewModel: AudioViewModel,
    progress: State<Boolean>
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.Color_AliceBlue))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Kutubxona",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )

        }

//            if (progress.value) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .width(60.dp)
//                            .height(60.dp)
//                            .align(Alignment.Center),
//                        color = Color.Gray,
//                        strokeWidth = 5.dp
//                    )
//                }
//            }


        LazyColumn {
            items(list) { byCategory ->
                categoryItem(ls = byCategory, viewModel::onEventDispatcherAudio)
            }
        }
    }
}


@Composable
fun bookItem(bookUIData: BookUIData, modifier: Modifier) {
    Column(
        modifier = modifier/*Modifier
            .width(160.dp)
            .height(300.dp)
            .padding(4.dp)
            .background(
                Color.White, shape = RoundedCornerShape(12.dp)
            )*/
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7F)
                .background(
                    colorResource(id = R.color.Color_Azure),
                    shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
                )
        ) {
            AsyncImage(
                model = bookUIData.bookImage,
                placeholder = painterResource(id = R.drawable.book_app_image),
                error = painterResource(id = R.drawable.book_app_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentDescription = null
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    text = bookUIData.bookName,
                    color = Color.Black,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,

                    fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    text = bookUIData.bookAuthor,
                    color = colorResource(id = R.color.gray_500),
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.nunito_bold)),
                    maxLines = 1
                )
            }


        }

    }
}

@Composable
fun categoryItem(ls: AudioDataForAdapter, onEventDispatcher: (AudioIntent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = ls.list[0].categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
            )
            Text(
                fontSize = 16.sp,
                color = colorResource(id = R.color.text_color),
                text = "Hammasi",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .clickable {
                        onEventDispatcher.invoke(AudioIntent.ClickAllAudio(ls))
                    }

            )

        }

        LazyRow {
            items(ls.list) { bookData ->
                bookItem(bookData, Modifier
                    .width(160.dp)
                    .height(300.dp)
                    .padding(4.dp)
                    .background(
                        Color.White, shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        onEventDispatcher.invoke(AudioIntent.ClickItemAudio(data = bookData))
                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewAudioContent() {
    AsaxiyCompose2Theme {
//        AudioContent()
    }
}