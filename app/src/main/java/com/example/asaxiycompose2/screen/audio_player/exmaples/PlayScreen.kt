//package com.example.asaxiycompose2.screen.audio_player.exmaples
//
//import android.annotation.SuppressLint
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.LinearOutSlowInEasing
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Slider
//import androidx.compose.material3.SliderDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.State
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.hilt.getViewModel
//import com.example.asaxiycompose2.R
//import org.orbitmvi.orbit.compose.collectAsState
//import org.orbitmvi.orbit.compose.collectSideEffect
//import com.example.asaxiycompose2.data.model.CommandEnum
//import com.example.asaxiycompose2.data.model.MusicData
//import com.example.asaxiycompose2.utils.MyEventBus
//import com.example.asaxiycompose2.utils.getTime
//import com.example.asaxiycompose2.utils.startMusicService
//import java.util.concurrent.TimeUnit
//
//
//class PlayScreen : Screen {
//
//    private val leftColor = Color(0xFFDFDFDF)
//    private val rightColor = Color(0xFFDFDFDF)
//
//    @Composable
//    override fun Content() {
//        val viewModel: PlayContract.ViewModel = getViewModel<PlayViewModel>()
//
//        val context = LocalContext.current
//
//        val uiState = viewModel.collectAsState()
//
//
//        PlayScreenContent(uiState, viewModel::onEventDispatcher)
//        viewModel.collectSideEffect { sideEffect ->
//            when (sideEffect) {
//                is PlayContract.SideEffect.UserAction -> {
//                    startMusicService(context, sideEffect.actionEnum)
//                }
//            }
//        }
//    }
//
//    @SuppressLint("StateFlowValueCalledInComposition", "RememberReturnType")
//    @Composable
//    fun PlayScreenContent(
//        uiState: State<PlayContract.UiState>,
//        onEventDispatcher: (PlayContract.Intent) -> Unit
//    ) {
//
//        val musicData = MyEventBus.currentMusicData.collectAsState(
//            initial = MusicData("Abdulbosit", "Maqsudov", "", 1000,"")
//        )
//
//        onEventDispatcher(PlayContract.Intent.CheckMusic(musicData.value!!))
//
//        val seekBarState = MyEventBus.currentTimeFlow.collectAsState(initial = 0)
//        var seekBarValue by remember { mutableStateOf(seekBarState.value) }
//        val musicIsPlaying = MyEventBus.isPlaying.collectAsState()
//
//
//        val isRepeated = MyEventBus.isRepeatedFlow.collectAsState()
//        val milliseconds = musicData.value!!.duration
//        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
//        val minutes = (milliseconds / 1000 / 60) % 60
//        val seconds = (milliseconds / 1000) % 60
//
//        val duration = if (hours == 0L) "%02d:%02d".format(minutes, seconds)
//        else "%02d:%02d:%02d".format(hours, minutes, seconds) // 03:45
//
//        var isSaved by remember { mutableStateOf(false) }
//
//
//        var currentRotation by remember { mutableStateOf(0f) }
//
//        val rotation = remember { Animatable(currentRotation) }
//
//
//        LaunchedEffect(MyEventBus.isPlaying.value) {
//            if (MyEventBus.isPlaying.value) {
//                // Infinite repeatable rotation when is playing
//                rotation.animateTo(
//                    targetValue = currentRotation + 360f,
//                    animationSpec = infiniteRepeatable(
//                        animation = tween(10000, easing = LinearEasing),
//                        repeatMode = RepeatMode.Restart
//                    )
//                ) {
//                    currentRotation = value
//                }
//            } else {
//                if (currentRotation > 0f) {
//                    // Slow down rotation on pause
//                    rotation.animateTo(
//                        targetValue = currentRotation + 50,
//                        animationSpec = tween(
//                            durationMillis = 2800,
//                            easing = LinearOutSlowInEasing
//                        )
//                    ) {
//                        currentRotation = value
//                    }
//                }
//            }
//        }
////        Vinyl(modifier = modifier.padding(24.dp), rotationDegrees = rotation.value)
//
//        val interaction = remember {
//            MutableInteractionSource()
//        }
//
//        when (uiState.value) {
//            is PlayContract.UiState.CheckMusic -> {
//                isSaved = (uiState.value as PlayContract.UiState.CheckMusic).isSaved
//            }
//            PlayContract.UiState.InitState -> {
//
//            }
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(
//                            leftColor,
//                            rightColor,
//                        )
//                    ),
//                )
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_back),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .size(30.dp)
//                        .clip(CircleShape)
//                        .clickable(interactionSource = interaction, indication = null) {
//                            onEventDispatcher(PlayContract.Intent.Back)
//                        }
//                )
//            }
//            Column(
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .padding(horizontal = 8.dp)
//                    .fillMaxWidth()
//                    .height(0.dp)
//                    .weight(1.7f)
//            ) {
//
//                if (musicData.value!!.albumArt != null)
//
//                    Box(
//                        modifier = Modifier
//                            .size(250.dp)
//                            .align(Alignment.CenterHorizontally)
//
//                    ) {
//
//                        Image(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(50))
//                                .size(250.dp)
//                                .rotate(rotation.value)
//                                .align(Alignment.Center),
//                            painter = painterResource(id = R.drawable.music_disk2),
//                            contentScale = ContentScale.Crop,
//                            contentDescription = null,
//                        )
//                        Image(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .clip(RoundedCornerShape(50))
//                                .size(100.dp)
//                                .rotate(rotation.value),
//                            painter = painterResource(id = R.drawable.ic_about),
//                            contentScale = ContentScale.Crop,
//                            contentDescription = null,
//                            alignment = Alignment.Center,
//                        )
//                        Spacer(
//                            modifier = Modifier
//                                .size(10.dp)
//                                .align(Alignment.Center)
//                                .clip(
//                                    RoundedCornerShape(50)
//                                )
//                                .background(
//                                    brush = Brush.horizontalGradient(
//                                        colors = listOf(
//                                            leftColor,
//                                            rightColor,
//                                        )
//                                    ),
//                                )
//
//                        )
//                    }
//                else {
//
//
//                    Image(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(50))
//                            .size(250.dp)
//                            .rotate(rotation.value)
//                            .align(Alignment.CenterHorizontally),
//                        painter = painterResource(id = R.drawable.music_disk2),
//                        contentScale = ContentScale.Crop,
//                        contentDescription = null
//                    )
//                }
//
//
//                Text(
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                    text = musicData.value!!.title ?: "Unknown",
//                    fontSize = 20.sp,
//                    overflow = TextOverflow.Ellipsis,
//                    maxLines = 1
//                )
//
//                Text(
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 4.dp),
//                    text = musicData.value!!.artist ?: "-- -- --",
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(0.dp)
//                    .weight(1f)
//            ) {
//                Slider(
//                    modifier = Modifier.padding(horizontal = 8.dp),
//                    value = seekBarState.value.toFloat(),
//                    onValueChange = { newState ->
//                        seekBarValue = newState.toInt()
//                        onEventDispatcher.invoke(PlayContract.Intent.UserAction(CommandEnum.UPDATE_SEEKBAR))
//                    },
//                    onValueChangeFinished = {
//                        MyEventBus.currentTime.value = seekBarValue
//                        onEventDispatcher.invoke(PlayContract.Intent.UserAction(CommandEnum.UPDATE_SEEKBAR))
//                    },
//                    valueRange = 0f..musicData.value!!.duration.toFloat(),
//                    steps = 1000,
//                    colors = SliderDefaults.colors(
//                        thumbColor = Color(0xFFa8dadc),
//                        activeTickColor = Color(0xFFFFFFFF),
//                        activeTrackColor = Color(0xFFCCC2C2),
//                        inactiveTickColor = Color.Gray,
//                        inactiveTrackColor = Color.Transparent
//
//                    )
//                )
//
//                // 00:00
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .width(0.dp)
//                            .weight(1f),
//                        text = getTime(seekBarState.value / 1000)
//                    )
//                    // 03:45
//                    Text(
//                        modifier = Modifier
//                            .width(0.dp)
//                            .weight(1f),
//                        textAlign = TextAlign.End,
//                        text = duration
//                    )
//                }
//
//
//                Spacer(modifier = Modifier.height(50.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//
//                    Box(modifier = Modifier.size(50.dp)) {
//                        Image(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clip(RoundedCornerShape(50))
//                                .padding(4.dp)
//                                .clickable {
//                                    onEventDispatcher(PlayContract.Intent.IsRepeated(MyEventBus.isRepeated))
//                                },
//                            painter = painterResource(id = R.drawable.ic_share),
//                            contentDescription = null
//                        )
//
//                        if (isRepeated.value) {
//                            Text(
//                                text = "1",
//                                Modifier
//                                    .size(26.dp)
//                                    .align(Alignment.Center),
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    }
//
//                    Image(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(50))
//                            .size(50.dp)
//                            .padding(8.dp)
//                            .clickable {
//                                onEventDispatcher.invoke(PlayContract.Intent.UserAction(CommandEnum.PREV))
//                                seekBarValue = 0
//                            },
//                        painter = painterResource(id = R.drawable.ic_previuos),
//                        contentDescription = null
//                    )
//
//                    Image(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .clip(RoundedCornerShape(50))
//                            .clickable {
//                                onEventDispatcher.invoke(
//                                    PlayContract.Intent.UserAction(
//                                        CommandEnum.MANAGE
//                                    )
//                                )
//                            },
//                        painter = painterResource(
//                            id = if (musicIsPlaying.value) R.drawable.ic_pause
//                            else R.drawable.ic_play
//                        ),
//                        contentDescription = null
//                    )
//
//                    Image(
//                        modifier = Modifier
//                            .rotate(180f)
//                            .size(50.dp)
//                            .clip(RoundedCornerShape(50))
//                            .padding(8.dp)
//
//                            .clickable {
//                                onEventDispatcher.invoke(PlayContract.Intent.UserAction(CommandEnum.NEXT))
//                                seekBarValue = 0
//                            },
//                        painter = painterResource(id = R.drawable.ic_previuos),
//                        contentDescription = null
//                    )
//
//
//
//
//
//
//                    /*IconButton(onClick = { *//*TODO*//* }) {
//
//                    }*/
//                    Image(
//                        painter = painterResource(id = if (isSaved) R.drawable.ic_like else R.drawable.ic_dislike),
//
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(50))
//                            .padding(6.dp)
//                            .size(42.dp)
//                            .padding(0.dp)
//                            .clickable(interactionSource = interaction, indication = null) {
//                                if (isSaved) {
//
//                                    onEventDispatcher(PlayContract.Intent.DeleteMusic(musicData.value!!))
//                                } else {
//
//                                    onEventDispatcher(PlayContract.Intent.SaveMusic(musicData.value!!))
//                                }
//                            },
//                        contentDescription = null,
//                    )
//                }
//            }
//        }
//    }
//
//
//    @Preview
//    @Composable
//    fun VinlyPrev() {
//        VinylAnimation(isPlaying = true)
//    }
//
//    @Composable
//    fun VinylAnimation(
//        modifier: Modifier = Modifier,
//        isPlaying: Boolean = false
//    ) {
//        // Allow resume on rotation
//        var currentRotation by remember { mutableStateOf(0f) }
//
//        val rotation = remember { Animatable(currentRotation) }
//
//        LaunchedEffect(isPlaying) {
//            if (isPlaying) {
//                // Infinite repeatable rotation when is playing
//                rotation.animateTo(
//                    targetValue = currentRotation + 360f,
//                    animationSpec = infiniteRepeatable(
//                        animation = tween(3000, easing = LinearEasing),
//                        repeatMode = RepeatMode.Restart
//                    )
//                ) {
//                    currentRotation = value
//                }
//            } else {
//                if (currentRotation > 0f) {
//                    // Slow down rotation on pause
//                    rotation.animateTo(
//                        targetValue = currentRotation + 50,
//                        animationSpec = tween(
//                            durationMillis = 1250,
//                            easing = LinearOutSlowInEasing
//                        )
//                    ) {
//                        currentRotation = value
//                    }
//                }
//            }
//        }
//        Vinyl(modifier = modifier.padding(24.dp), rotationDegrees = rotation.value)
//    }
//
//
//    @Composable
//    fun Vinyl(
//        modifier: Modifier = Modifier,
//        rotationDegrees: Float = 0f
//    ) {
//        Box(
//            modifier = modifier
//                .aspectRatio(1.0f)
//        ) {
//
//            // Vinyl background
//            Image(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .rotate(rotationDegrees),
//                painter = painterResource(id = R.drawable.music_disk2),
//                contentDescription = ""
//            )
//
//            // Vinyl lights effect
//
//
//            // Vinyl 'album' cover
//            // For using with Coil or Glide, wrap into surface with shape
//
//        }
//    }
//
//}
