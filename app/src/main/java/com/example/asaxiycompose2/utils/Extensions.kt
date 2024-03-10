package com.example.asaxiycompose2.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Build
import android.widget.SeekBar
import com.example.asaxiycompose2.data.model.CommandEnum
import com.example.asaxiycompose2.screen.service.MusicService
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Extensions{
    companion object{
        private val symbols: DecimalFormatSymbols = DecimalFormatSymbols()
        private val dfBalance: DecimalFormat = DecimalFormat("###,###,###,###", symbols)
        fun formatBalance(value: Long): String = dfBalance.format(value).replace(',', ' ')

    }
}
fun startMusicService(context: Context, commandEnum: CommandEnum) {
    val intent = Intent(context, MusicService::class.java)
    intent.putExtra("COMMAND", commandEnum)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else context.startService(intent)
}
fun String.removeSpace(): String = this.replace("\\s+".toRegex(), "")

fun SeekBar.setChangeProgress(block: (progress: Int, fromUser: Boolean) -> Unit) {
    this.setOnSeekBarChangeListener(object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            /*Timber.d("onProgressChanged: ${seekBar?.progress}")
            Timber.d("onProgressChanged: $progress")
            Timber.d("onProgressChanged: $fromUser")*/
            seekBar?.let { block.invoke(it.progress, fromUser) }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            /*Timber.d("onStartTrackingTouch: ${seekBar?.progress}")*/
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            Timber.d("onStopTrackingTouch: ${seekBar?.progress}")
            seekBar?.let { block.invoke(it.progress, false) }
        }
    })
}
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("mm:ss", Locale.getDefault())
    return format.format(date)
}
//fun convertLongToProgress(time: Long): Int = (time * 100 / AudioManager.duration).toInt()
fun getAlbumImage(path: String): Bitmap? {
    val mmr: MediaMetadataRetriever = MediaMetadataRetriever()
    mmr.setDataSource(path)
    val data: ByteArray? = mmr.embeddedPicture
    return when {
        data != null -> BitmapFactory.decodeByteArray(data, 0, data.size)
        else -> null
    }
}