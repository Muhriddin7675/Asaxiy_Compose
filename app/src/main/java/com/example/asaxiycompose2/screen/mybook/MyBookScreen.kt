package com.example.asaxiycompose2.screen.mybook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.data.model.MyBooksUiData
import com.example.asaxiycompose2.data.model.StatusEnum
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme

class MyBookScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<MyBookViewModel>()
        viewModel.onEventDispatcher(MyBookIntent.LoadData)
        val bookList by viewModel.loadDataSharedFlow.collectAsState(initial = null)
        if (bookList == null || bookList!!.isEmpty()) {
            MyBookContent(bookList = emptyList(), viewModel = viewModel)
        }
        else {
            MyBookContent(bookList = bookList!!, viewModel)
        }
    }
}

@Composable
fun MyBookContent(bookList: List<MyBooksUiData>, viewModel: MyBookViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

    }
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
                text = "Mening Kitoblarim",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )

        }
        LazyColumn {

            items(bookList) { bookData ->
                BookCard(bookData, viewModel::onEventDispatcher)
            }
        }

    }
}

@Composable
fun BookCard(bookData: MyBooksUiData, onEventDispatcher: (MyBookIntent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
            .clickable {
                val data = BookUIData(
                    bookDocID = bookData.docId,
                    bookName = bookData.bookName,
                    bookAuthor = bookData.bookAuthor,
                    bookImage = bookData.imagePath,
                    bookDescription = bookData.description,
                    bookSize = bookData.bookSize,
                    bookPath = bookData.bookPath,
                    categoryName = "",
                    category = "",
                    type = bookData.status.name,
                )
                onEventDispatcher.invoke(
                    MyBookIntent.ClickItem(data)
                )
            }
            .background(Color.White, RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(130.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.book_app_image),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                )
                AsyncImage(
                    model = bookData.imagePath,
                    placeholder = painterResource(id = R.drawable.book_app_image),
                    error = painterResource(id = R.drawable.book_app_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    text = bookData.bookName,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = bookData.bookAuthor,
                    fontSize = 17.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 10.dp, end = 30.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .background(Color(0xfffff7eb), RoundedCornerShape(8.dp))
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = when (bookData.status) {
                    StatusEnum.PDF -> {
                        painterResource(id = R.drawable.read_book)
                    }

                    StatusEnum.AUDIO -> {
                        painterResource(id = R.drawable.ic_audio)
                    }
                },
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(Color(0xFFFF9800)),
//                contentScale = ContentScale.Fit,
//                colorFilter = ColorFilter.tint(LocalContentColor.current),
//                alpha = LocalContentAlpha.current,
            )
        }
    }
}

@Preview
@Composable
fun PreviewMyBookScreen() {
    AsaxiyCompose2Theme {
//        MuBookContent()
    }
}