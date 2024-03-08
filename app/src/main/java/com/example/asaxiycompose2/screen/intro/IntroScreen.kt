package com.example.asaxiycompose2.screen.intro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme
import kotlinx.coroutines.launch

class IntroScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<IntroViewModel>()
        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(0.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(0.dp)
            ) { it ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (it) {
                        0 -> {
                            Page1(
                                R.string.elektron_kitoblar,
                                R.string.intro_text_first,
                                R.drawable.intro_first
                            )
                        }

                        1 -> {
                            Page1(
                                R.string.audio_kitoblar,
                                R.string.intro_text_second,
                                R.drawable.intro_second
                            )

                        }

                        2 -> {
                            Page1(
                                R.string.cheksiz_bilim,
                                R.string.intro_text_tread,
                                R.drawable.intro_thread
                            )

                        }
                    }
                }
            }

            Button(
                onClick = {
                    val nextPageIndex = pagerState.currentPage + 1
                    coroutineScope.launch { pagerState.animateScrollToPage(nextPageIndex) }
                    if(nextPageIndex == 3){
                        viewModel.onEventDispatcher(IntroIntent.OpenLogin)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.BottomCenter)
            )
            {
                if (pagerState.currentPage == 2) {
                    Text(text = "Next")
                } else {
                    Text(text = "Keyingisi")
                }

            }
        }

    }
}

@Preview
@Composable
fun PreviewIntroPage() {
    AsaxiyCompose2Theme {

    }
}

@Composable
fun Page1(textTitle: Int, text: Int, image: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp, 250.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = textTitle),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = text),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 24.dp, end = 24.dp)
            )
        }
    }
}
