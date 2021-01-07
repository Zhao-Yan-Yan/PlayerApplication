package com.zy.library_player

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleObserver
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
class VideoView : FrameLayout, PlayControl, LifecycleObserver {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val viewBinding = VideoMainBinding.inflate(LayoutInflater.from(context), this, true)
    private val controlDelegate by lazy { ControlDelegate(this, viewBinding.controller) }
    private val playerFactory: PlayerFactory<MyAliPlayer> = MyAliPlayerFactory.create()
    private val player: AbstractPlayer by lazy { playerFactory.createPlayer(context) }
    private val activity by lazy { scanForActivity(context) }

    init {
        viewBinding.controller.attachDelegate(controlDelegate)
        player.initPlayer()
        player.setDisplay(viewBinding.surfaceView)
        activity?.let {
            player.playStatus.observe(it, { status ->
                keepScreenOn = status == PlayStatus.PLAYING
                viewBinding.controller.handlePlayStatusChanged(status)
            })

            player.currentDuration.observe(it, { l ->
                viewBinding.controller.handleDurationChanged(getDuration(), getCurrentDuration())
            })
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
}