package com.example.asaxiycompose2.screen.audio_player

import android.media.MediaPlayer
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.data.model.AudioPlayerData
import java.util.concurrent.TimeUnit


class PlayScreen(audioPlayerData: AudioPlayerData) : Screen {
    private val musicData = audioPlayerData
    private lateinit var mediaPlayer: MediaPlayer

    @Composable
    override fun Content() {
        mediaPlayer = MediaPlayer.create(LocalContext.current, musicData.file.toUri())
        PlayScreenContent()
    }

//    @Composable
//    fun PlayPrev() {
//        AsaxiyCompose2Theme {
//            PlayScreenContent()
//        }
//    }

    @Composable
    fun PlayScreenContent() {

        val milliseconds = mediaPlayer.duration
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds.toLong())
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
                .background(Color.White)
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
                    AsyncImage(
                        model = musicData.bookUIData.bookImage,
                        placeholder = painterResource(id = R.drawable.book_app_image),
                        error = painterResource(id = R.drawable.book_app_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.Center),
                        contentDescription = null
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
                        text = musicData.bookUIData.bookName,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Text(
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        text = musicData.bookUIData.bookAuthor,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(56.dp))


                    var stateSlider by remember { mutableStateOf(0.1f) }
                    Slider(
                        modifier = Modifier.padding(horizontal = 8.dp),
//                        value = seekBarState.value.toFloat(),
                        value = 100f,
                        onValueChange = { newState ->
//                            seekBarValue = newState.toInt()
//                            onEventDispatcher.invoke(PlayContract.Intent.UserAction(CommandEnum.UPDATE_SEEKBAR))
                        },
                        onValueChangeFinished = {
//                            MyEventBus.currentTime.value = seekBarValue
//                            onEventDispatcher.invoke(PlayContract.Intent.UserAction(CommandEnum.UPDATE_SEEKBAR))
                        },
//                        valueRange = 0f..musicData.value!!.duration.toFloat(),
                        steps = 1000,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFa8dadc),
                            activeTickColor = Color(0xFFFFFFFF),
                            activeTrackColor = Color(0xFFCCC2C2),
                            inactiveTickColor = Color.Gray,
                            inactiveTrackColor = Color.Transparent

                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Text(
                            modifier = Modifier
                                .width(0.dp)
                                .weight(1f),
                            text = duration
                        )
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

                        var manageState by remember { mutableIntStateOf(0) }
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50))
                                .clickable {
                                    manageState = if (mediaPlayer.isPlaying) {
                                        mediaPlayer.pause()
                                        0

                                    } else {
                                        mediaPlayer.start()
                                        1
                                    }
                                },
                            painter = painterResource(
                                id  = if(manageState == 0) R.drawable.ic_play else R.drawable.ic_pause
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
