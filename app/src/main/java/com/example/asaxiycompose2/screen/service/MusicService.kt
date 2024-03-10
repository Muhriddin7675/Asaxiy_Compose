package com.example.asaxiycompose2.screen.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.telephony.TelephonyManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

class MusicService : Service() {

    companion object {
        const val CHANNEL_ID = "My music player"
        const val CHANNEL_NAME = "Music player"
    }

//    private val callReceiver = CallReceiver()

    private var _musicPlayer: MediaPlayer? = null
    private val musicPlayer get() = _musicPlayer!!

    override fun onBind(intent: Intent?): IBinder? = null
    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        val intentFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
//        registerReceiver(callReceiver, intentFilter)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

//    private fun createNotification(musicData: MusicData) {
//
//        val myIntent = Intent(this, MainActivity::class.java).apply {
//            Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val myPendingIntent =
//            PendingIntent.getActivity(this, 1, myIntent, PendingIntent.FLAG_IMMUTABLE)
//
//        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.music_disk2)
//            .setCustomContentView(createRemoteView(musicData))
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            .setContentIntent(myPendingIntent)
////            .setColor(Light_Red.toArgb())
//            .setColorized(true)
//            .setOnlyAlertOnce(true)
//            .setOngoing(true)
//        startForeground(1, notificationBuilder.build())
//    }

//    private fun createRemoteView(musicData: MusicData): RemoteViews {
//        val view = RemoteViews(this.packageName, R.layout.remote_view)
//        view.setTextViewText(R.id.textMusicName, musicData.title)
//        view.setTextViewText(R.id.textArtistName, musicData.artist)
//        if (musicData.albumArt != null)
//            view.setImageViewBitmap(R.id.disk, musicData.albumArt)
//
//
//        if (_musicPlayer != null && !musicPlayer.isPlaying) {
//            view.setImageViewResource(R.id.buttonManage, R.drawable.play)
//        } else {
//            view.setImageViewResource(R.id.buttonManage, R.drawable.pause)
//        }
//
//        view.setOnClickPendingIntent(R.id.buttonPrev, createPendingIntent(CommandEnum.PREV))
//        view.setOnClickPendingIntent(R.id.buttonManage, createPendingIntent(CommandEnum.MANAGE))
//        view.setOnClickPendingIntent(R.id.buttonNext, createPendingIntent(CommandEnum.NEXT))
//        view.setOnClickPendingIntent(R.id.buttonCancel, createPendingIntent(CommandEnum.CLOSE))
//        return view
//    }
//
//    private fun createPendingIntent(commandEnum: CommandEnum): PendingIntent {
//        val intent = Intent(this, MusicService::class.java)
//        intent.putExtra("COMMAND", commandEnum)
//        return PendingIntent.getService(
//            this,
//            commandEnum.amount,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (MyEventBus.currentCursorEnum == CursorEnum.STORAGE &&
//            (MyEventBus.storageCursor == null || MyEventBus.storagePos == -1)
//        ) return START_NOT_STICKY
//        else if (MyEventBus.currentCursorEnum == CursorEnum.SAVED &&
//            (MyEventBus.roomCursor == null || MyEventBus.roomPos == -1)
//        ) return START_NOT_STICKY
//
//        val command = intent?.extras?.getSerializable("COMMAND") as CommandEnum
//        doneCommand(command)
//        if (command.name != CommandEnum.CLOSE.name && MyEventBus.currentCursorEnum == CursorEnum.SAVED) {
//            createNotification(MyEventBus.roomCursor!!.getMusicDataByPosition(MyEventBus.roomPos))
//        } else if (command.name != CommandEnum.CLOSE.name && MyEventBus.currentCursorEnum == CursorEnum.STORAGE) {
//            createNotification(MyEventBus.storageCursor!!.getMusicDataByPosition(MyEventBus.storagePos))
//        }
//        return START_NOT_STICKY
//    }
//
//    private fun doneCommand(commandEnum: CommandEnum) {
//        when (commandEnum) {
//            CommandEnum.MANAGE -> {
//                if (musicPlayer.isPlaying) doneCommand(CommandEnum.PAUSE)
//                else doneCommand(CommandEnum.CONTINUE)
//            }
//
//            CommandEnum.CONTINUE -> {
//                job = moveProgress().onEach { MyEventBus.currentTimeFlow.emit(it) }.l<aunchIn(scope)
//                musicPlayer.seekTo(MyEventBus.currentTime.value)
//                scope.launch { MyEventBus.isPlaying.emit(true) }
//                musicPlayer.start()
//            }
//
//            CommandEnum.UPDATE_SEEKBAR -> {
//                if (musicPlayer.isPlaying) {
//                    job?.cancel()
//                    musicPlayer.seekTo(MyEventBus.currentTime.value)
//                    job = moveProgress().onEach { MyEventBus.currentTimeFlow.emit(it) }
//                        .launchIn(scope)
//                } else {
//                    musicPlayer.seekTo(MyEventBus.currentTime.value)
//                    job = moveProgress().onEach { MyEventBus.currentTimeFlow.emit(it) }
//                        .launchIn(scope)
//                    job?.cancel()
//                }
//            }
//
//            CommandEnum.PLAY -> {
//                val data =
//                    if (MyEventBus.currentCursorEnum == CursorEnum.SAVED) MyEventBus.roomCursor!!.getMusicDataByPosition(MyEventBus.roomPos)
//                    else MyEventBus.storageCursor!!.getMusicDataByPosition(MyEventBus.storagePos)
//
//                scope.launch { MyEventBus.currentMusicData.emit(data) }
//
//                MyEventBus.currentTime.value = 0
//                MyEventBus.totalTime = data.duration.toInt()
//                _musicPlayer?.stop()
//                _musicPlayer = MediaPlayer.create(this, Uri.parse(data.data))
//                musicPlayer.seekTo(MyEventBus.currentTime.value)
//                musicPlayer.setOnCompletionListener { doneCommand(CommandEnum.NEXT) }
//
//                job?.cancel()
//                job = moveProgress().onEach { MyEventBus.currentTimeFlow.emit(it) }.launchIn(scope)
//                scope.launch { MyEventBus.isPlaying.emit(true) }
//
//                musicPlayer.start()
//            }
//
//            CommandEnum.PAUSE -> {
//                musicPlayer.pause()
//                MyEventBus.currentTime.value = MyEventBus.currentTimeFlow.value
//                musicPlayer.seekTo(MyEventBus.currentTime.value)
//                job?.cancel()
//                scope.launch { MyEventBus.isPlaying.emit(false) }
//            }
//
//            CommandEnum.CLOSE -> {
//                musicPlayer.pause()
//                job?.cancel()
//                scope.launch { MyEventBus.isPlaying.emit(false) }
//                ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
//            }
//
//            CommandEnum.IS_REPEATED -> {
//                MyEventBus.isRepeated = !MyEventBus.isRepeated
//                scope.launch {
//                    MyEventBus.isRepeatedFlow.emit(!MyEventBus.isRepeatedFlow.value)
//                }
//            }
//        }
//    }
//
//    private fun moveProgress(): Flow<Int> = flow {
//        for (i in MyEventBus.currentTime.value until MyEventBus.totalTime step 1000) {
//            emit(i)
//            delay(1000)
//        }
//    }
}