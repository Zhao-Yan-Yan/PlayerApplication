package com.zy.library_player

import com.zy.library_player.status.PlayStatus

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: 播放控制
 * @CreateDate: 2021/1/7 18:21
 */
interface PlayControl {
    fun start()

    fun pause()

    fun resume()

    fun release()

    fun seekTo(position: Long)

    fun setSpeed(speed: Float)

    fun getSpeed(): Float

    fun getPlayStatus(): PlayStatus

    fun getDuration(): Long

    fun getCurrentDuration(): Long
}