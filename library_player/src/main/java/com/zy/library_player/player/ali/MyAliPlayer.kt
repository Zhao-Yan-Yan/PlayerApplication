package com.zy.library_player.player.ali

import android.content.Context
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.aliyun.player.AliPlayer
import com.aliyun.player.AliPlayerFactory
import com.aliyun.player.IPlayer
import com.aliyun.player.bean.InfoCode
import com.aliyun.player.source.UrlSource
import com.blankj.utilcode.util.LogUtils
import com.zy.library_player.player.AbstractPlayer
import com.zy.library_player.status.PlayStatus

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 18:01
 */
class MyAliPlayer(val context: Context) : AbstractPlayer() {

    private lateinit var player: AliPlayer

    override fun initPlayer() {
        player = AliPlayerFactory.createAliPlayer(context.applicationContext)
        val config = player.config.apply {
            //最大延迟。注意：直播有效。当延时比较大时，播放器sdk内部会追帧等，保证播放器的延时在这个范围内。
            mMaxDelayTime = 5000
            // 最大缓冲区时长。单位ms。播放器每次最多加载这么长时间的缓冲数据。
            mMaxBufferDuration = 50000
            //高缓冲时长。单位ms。当网络不好导致加载数据时，如果加载的缓冲时长到达这个值，结束加载状态。
            mHighBufferDuration = 3000
            // 起播缓冲区时长。单位ms。这个时间设置越短，起播越快。也可能会导致播放之后很快就会进入加载状态。
            mStartBufferDuration = 500
            //设置网络超时时间，单位ms
            mNetworkTimeout = 20000
            //设置超时重试次数。每次重试间隔为networkTimeout。networkRetryCount=0则表示不重试，重试策略app决定，默认值为2
            mNetworkRetryCount = 3
        }
        player.config = config

        player.setOnCompletionListener {
            LogUtils.e("播放完成 Completion")
        }

        player.setOnErrorListener {
            LogUtils.e("播放Error", it.code, it.extra, it.msg)
        }

        player.setOnPreparedListener {
            LogUtils.e("prepared 准备成功")
        }

        player.setOnVideoSizeChangedListener { width, height ->
            LogUtils.e("videoSizeChanged", width, height)
        }

        player.setOnRenderingStartListener {
            LogUtils.e("RenderingStart 首帧渲染")
        }

        player.setOnInfoListener {
            //BufferedPosition
            //CurrentPosition
            when (it.code) {
                InfoCode.Unknown -> Unit
                InfoCode.LoopingStart -> Unit
                InfoCode.BufferedPosition -> currentBufferDuration.postValue(it.extraValue)
                InfoCode.CurrentPosition -> currentDuration.postValue(it.extraValue)
                InfoCode.AutoPlayStart -> Unit
                InfoCode.SwitchToSoftwareVideoDecoder -> Unit
                InfoCode.AudioCodecNotSupport -> Unit
                InfoCode.AudioDecoderDeviceError -> Unit
                InfoCode.VideoCodecNotSupport -> Unit
                InfoCode.VideoDecoderDeviceError -> Unit
                InfoCode.NetworkRetry -> Unit
                InfoCode.CacheSuccess -> Unit
                InfoCode.CacheError -> Unit
                InfoCode.LowMemory -> Unit
            }
            LogUtils.e("setOnInfoListener", it.code, it.extraMsg, it.extraValue)
        }

        player.setOnSeekCompleteListener {
            LogUtils.e("setOnSeekCompleteListener seek完成")
        }

        player.setOnStateChangedListener {
            LogUtils.e("状态改变", it)
            when (it) {
                IPlayer.completion -> playStatus.postValue(PlayStatus.COMPLETED)
                IPlayer.error -> playStatus.postValue(PlayStatus.ERROR)
                IPlayer.idle -> playStatus.postValue(PlayStatus.IDLE)
                IPlayer.paused -> playStatus.postValue(PlayStatus.PAUSED)
                IPlayer.prepared -> playStatus.postValue(PlayStatus.PREPARED)
                IPlayer.started -> playStatus.postValue(PlayStatus.PLAYING)
                IPlayer.unknow -> playStatus.postValue(PlayStatus.UN_KNOW)
            }
        }

        player.setOnSnapShotListener { bitmap, width, height ->
            LogUtils.e("截图", bitmap, width, height)
        }
    }

    override fun play(url: String) {
        setDataSource(url)
        prepare()
        start()
    }

    override fun setDisplay(surfaceView: SurfaceView) {
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                player.setDisplay(holder)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                player.redraw()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                player.setDisplay(null)
            }
        })
    }

    override fun setDataSource(url: String) {
        val urlSource = UrlSource()
        urlSource.uri = url
        player.setDataSource(urlSource)
    }

    override fun prepare() {
        player.prepare()
    }

    override fun start() {
        player.start()
    }

    override fun pause() {
        player.pause()
    }

    override fun stop() {
        player.stop()
    }

    override fun resume() {
        player.start()
    }

    override fun release() {
        player.release()
    }

    override fun reset() {
        player.reset()
    }

    override fun getCurrentPosition(): Long {
        return currentDuration.value ?: 0
    }

    override fun getDuration(): Long {
        return player.duration
    }

    override fun getSpeed(): Float {
        return player.speed
    }

    override fun setSpeed(speed: Float) {
        player.speed = speed
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun getPlayStatus(): PlayStatus {
        return playStatus.value ?: PlayStatus.IDLE
    }
}