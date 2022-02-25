package com.Forte.echo

import android.graphics.drawable.Drawable
import java.util.concurrent.TimeUnit

data class MusicDataList(var imgSrc: Int, var songTitle: String, var songDesc: String, var songUrl: String, var Count: Int, var Duration: Long)
    fun formatDuration(Duration: Long): CharSequence? {
        val minutes = TimeUnit.MINUTES.convert(Duration,TimeUnit.MILLISECONDS)
        val seconds = (TimeUnit.SECONDS.convert(Duration,TimeUnit.MILLISECONDS) - minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))
        return String.format("%2d:%2d",minutes,seconds)
    }
