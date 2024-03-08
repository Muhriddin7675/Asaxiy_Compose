package com.example.asaxiycompose2.screen.audio_player

import android.widget.SeekBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme
import java.util.concurrent.TimeUnit


class PlayScreen : Screen {
    private val musicData = R.raw.taqiq

    @Composable
    override fun Content() {
        PlayScreenContent()
    }

    @Preview
    @Composable
    fun PlayPrev() {
        AsaxiyCompose2Theme {
            PlayScreenContent()
        }
    }

    @Composable
    fun PlayScreenContent() {

        val milliseconds = 1000L
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes = (milliseconds / 1000 / 60) % 60
        val seconds = (milliseconds / 1000) % 60
        val rightColor = Color(0xFF3F51B5)

        val duration = if (hours == 0L) "%02d:%02d".format(minutes, seconds)
        else "%02d:%02d:%02d".format(hours, minutes, seconds) // 03:45

        val isSaved by remember { mutableStateOf(false) }

        val leftColor = Color(0xFF3F51B5)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(0.dp)
                    .weight(1.7f)
            ) {

                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.CenterHorizontally)

                ) {

                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .size(250.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_audio),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .size(250.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.dunyoning_ishlari),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                    Spacer(
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.Center)
                            .clip(
                                RoundedCornerShape(50)
                            )
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        leftColor,
                                        rightColor,
                                    )
                                ),
                            )

                    )

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(1f)
                ) {
                    // 00:00


                    Spacer(modifier = Modifier.height(42.dp))
                    Text(
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = "Title" ?: "Unknown",
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Text(
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        text = "Artist" ?: "-- -- --",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(56.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Text(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f),
                            text = "03:45"
                        )
                        // 03:45
                        Text(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f),
                            textAlign = TextAlign.End,
                            text = duration
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        Box(modifier = Modifier.size(50.dp)) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50))
                                    .padding(4.dp)
                                    .clickable {

                                    },
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null
                            )
                        }

                        Image(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(50.dp)
                                .padding(8.dp)
                                .clickable {

                                },
                            painter = painterResource(id = R.drawable.ic_previuos),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50)),
                            painter = painterResource(
                                id = R.drawable.ic_play
                            ),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(50))
                                .padding(8.dp),
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_filter),
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .padding(6.dp)
                                .size(42.dp)
                                .padding(0.dp),
                            contentDescription = null,
                        )
                    }
                }
            }
        }

    }

}
