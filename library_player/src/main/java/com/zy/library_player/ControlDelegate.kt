package com.zy.library_player

import com.zy.library_player.status.PlayStatus

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 17:23
 */
class ControlDelegate(
    private val playControl: PlayControl,
    private val viewControl: ViewControl
) : PlayControl, ViewControl {
    override fun start() {
        playControl.start()
    }

    override fun pause() {
        playControl.pause()
    }

    override fun resume() {
        playControl.resume()
    }

    override fun release() {
        playControl.release()
    }

    override fun seekTo(position: Long) {
        playControl.seekTo(position)
    }

    override fun setSpeed(speed: Float) {
        playControl.setSpeed(speed)
    }

    override fun getSpeed(): Float {
        return playControl.getSpeed()
    }

    override fun getPlayStatus(): PlayStatus {
        return playControl.getPlayStatus()
    }

    override fun getDuration(): Long {
        return playControl.getDuration()
    }

    override fun getCurrentDuration(): Long {
        return playControl.getCurrentDuration()
    }

    override fun startProgress() {
        viewControl.startProgress()
    }

    override fun stopProgress() {
        viewControl.stopProgress()
    }

    fun isPlaying() = getPlayStatus() == PlayStatus.PLAYING
}