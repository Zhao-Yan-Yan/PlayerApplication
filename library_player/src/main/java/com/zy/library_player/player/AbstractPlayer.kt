package com.zy.library_player.player

import android.view.SurfaceView
import androidx.lifecycle.MutableLiveData
import com.zy.library_player.status.PlayStatus

/**
 * @ProjectName: YPlayer
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/6 10:15
 */
abstract class AbstractPlayer {
    val playStatus: MutableLiveData<PlayStatus> = MutableLiveData()

    val currentDuration: MutableLiveData<Long> = MutableLiveData()

    val currentBufferDuration: MutableLiveData<Long> = MutableLiveData()

    abstract fun initPlayer()

    abstract fun play(url: String)

    abstract fun setDisplay(surfaceView: SurfaceView)

    abstract fun setDataSource(url: String)

    abstract fun prepare()

    abstract fun start()

    abstract fun pause()

    abstract fun stop()

    abstract fun resume()

    abstract fun release()

    abstract fun reset()

    abstract fun getCurrentPosition(): Long

    abstract fun getDuration(): Long

    abstract fun getSpeed(): Float

    abstract fun setSpeed(speed: Float)

    abstract fun seekTo(position: Long)

    abstract fun getPlayStatus(): PlayStatus

}