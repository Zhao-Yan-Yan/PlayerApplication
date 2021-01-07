package com.zy.library_player

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleObserver
import com.zy.library_player.controll.ControlComponent
import com.zy.library_player.databinding.VideoMainBinding
import com.zy.library_player.player.AbstractPlayer
import com.zy.library_player.player.PlayerFactory
import com.zy.library_player.player.ali.MyAliPlayer
import com.zy.library_player.player.ali.MyAliPlayerFactory
import com.zy.library_player.status.PlayStatus

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 17:21
 */
class VideoView : FrameLayout, PlayControl, ViewControl, LifecycleObserver {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val controlDelegate by lazy { ControlDelegate(this, this) }
    private val viewBinding = VideoMainBinding.inflate(LayoutInflater.from(context), this, true)
    private val playerFactory: PlayerFactory<MyAliPlayer> = MyAliPlayerFactory.create()
    private val player: AbstractPlayer by lazy { playerFactory.createPlayer(context) }
    private val activity by lazy { scanForActivity(context) }
    private val controlComponents = mutableListOf<ControlComponent>()

    init {
        player.initPlayer()
        player.setDisplay(viewBinding.surfaceView)
        add(
            viewBinding.controlLeft,
            viewBinding.controlTop,
            viewBinding.controlRight,
            viewBinding.controlBottom
        )

        activity?.let {
            player.playStatus.observe(it, { status ->
                keepScreenOn = status == PlayStatus.PLAYING
                handlePlayStatusChanged(status)
            })

            player.currentDuration.observe(it, { l ->
                handleDurationChanged(getDuration(), getCurrentDuration())
            })
        }
    }

    private fun add(vararg controlComponent: ControlComponent) {
        controlComponent.forEach {
            controlComponents.add(it)
            it.attach(controlDelegate)
        }
    }

    private fun handleDurationChanged(duration: Long, position: Long) {
        controlComponents.forEach {
            it.progressChanged(duration, position)
        }
    }

    private fun handlePlayStatusChanged(status: PlayStatus) {
        controlComponents.forEach {
            it.playStateChanged(status)
        }
    }

    private fun handleSetProgress(duration: Long, position: Long) {
        controlComponents.forEach {
            it.progressChanged(duration, position)
        }
    }

    fun play(url: String) {
        player.play(url)
        player.setSpeed(2.0f)
    }

    override fun start() {
        player.start()
    }

    override fun pause() {
        player.pause()
    }

    override fun resume() {
        player.resume()
    }

    override fun release() {
        player.release()
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun setSpeed(speed: Float) {
        player.setSpeed(speed)
    }

    override fun getSpeed(): Float {
        return player.getSpeed()
    }

    override fun getPlayStatus(): PlayStatus {
        return player.getPlayStatus()
    }

    override fun getCurrentDuration(): Long {
        return player.getCurrentPosition()
    }

    override fun getDuration(): Long {
        return player.getDuration()
    }

    override fun startProgress() {
        post(mShowProgress)
    }

    override fun stopProgress() {
        removeCallbacks(mShowProgress)
    }

    /**
     * 刷新进度Runnable
     */
    private var mShowProgress: Runnable = object : Runnable {
        override fun run() {
            val position = controlDelegate.getCurrentDuration()
            val duration = controlDelegate.getDuration()
            handleSetProgress(duration, position)
            if (controlDelegate.isPlaying()) {
                postDelayed(this, ((1000 - position % 1000) / controlDelegate.getSpeed()).toLong())
            }
        }
    }
}