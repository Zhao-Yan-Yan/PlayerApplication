package com.zy.library_player

import android.content.Context
import android.content.ContextWrapper
import androidx.core.app.ComponentActivity
import java.util.*

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 18:43
 */
fun scanForActivity(context: Context?): ComponentActivity? {
    return when (context) {
        is ComponentActivity -> context
        is ContextWrapper -> scanForActivity(context)
        else -> null
    }
}

fun stringForTime(timeMs: Long): String {
    val totalSeconds = timeMs / 1000
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    return if (hours > 0) {
        String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}