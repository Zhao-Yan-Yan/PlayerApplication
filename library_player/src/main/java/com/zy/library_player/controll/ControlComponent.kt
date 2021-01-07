package com.zy.library_player.controll

import android.view.animation.Animation
import com.zy.library_player.ControlDelegate
import com.zy.library_player.status.PlayStatus
import com.zy.library_player.status.WindowMode

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 17:22
 */
interface ControlComponent {
    fun attach(controlDelegate: ControlDelegate)

    fun visibilityChanged(isVisible: Boolean, anim: Animation) {}

    fun playStateChanged(status: PlayStatus) {}

    fun windowModeChange(windowMode: WindowMode) {}

    fun lockStateChanged(isLocked: Boolean) {}

    fun progressChanged(duration: Long, position: Long) {}
}