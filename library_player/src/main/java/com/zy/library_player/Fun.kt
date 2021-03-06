package com.zy.library_player

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Point
import android.os.Build
import android.view.*
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

/**
 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
 */
fun getWindowManager(context: Context): WindowManager? {
    return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
}

/**
 * 获取屏幕宽度
 */
fun getScreenWidth(context: Context, isIncludeNav: Boolean): Int {
    return if (isIncludeNav) {
        context.resources.displayMetrics.widthPixels + getNavigationBarHeight(context)
    } else {
        context.resources.displayMetrics.widthPixels
    }
}

/**
 * 获取屏幕高度
 */
fun getScreenHeight(context: Context, isIncludeNav: Boolean): Int {
    return if (isIncludeNav) {
        context.resources.displayMetrics.heightPixels + getNavigationBarHeight(context)
    } else {
        context.resources.displayMetrics.heightPixels
    }
}

/**
 * 获取NavigationBar的高度
 */
fun getNavigationBarHeight(context: Context): Int {
    if (!hasNavigationBar(context)) {
        return 0
    }
    val resources = context.resources
    val resourceId = resources.getIdentifier(
        "navigation_bar_height",
        "dimen", "android"
    )
    //获取NavigationBar的高度
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 是否存在NavigationBar
 */
fun hasNavigationBar(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        val display = getWindowManager(context)?.getDefaultDisplay()
        val size = Point()
        val realSize = Point()
        display?.getSize(size)
        display?.getRealSize(realSize)
        realSize.x != size.x || realSize.y != size.y
    } else {
        val menu = ViewConfiguration.get(context).hasPermanentMenuKey()
        val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        !(menu || back)
    }
}
