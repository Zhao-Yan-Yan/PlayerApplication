package com.zy.library_player.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zy.library_player.ControlDelegate
import com.zy.library_player.controll.ControlComponent
import com.zy.library_player.databinding.ControlBottomViewBinding
import com.zy.library_player.status.PlayStatus
import com.zy.library_player.stringForTime

/**
 * @ProjectName: YPlayer
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/6 11:20
 */
class ControlBottomView : FrameLayout, ControlComponent {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private lateinit var controlDelegate: ControlDelegate
    private val viewBinding = ControlBottomViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {

    }

    override fun attach(controlDelegate: ControlDelegate) {
        this.controlDelegate = controlDelegate
    }

    override fun playStateChanged(status: PlayStatus) {
        super.playStateChanged(status)

        when (status) {
            PlayStatus.PLAYING -> {
                controlDelegate.startProgress()
            }
        }
    }

    override fun progressChanged(duration: Long, position: Long) {
        super.progressChanged(duration, position)
        viewBinding.seekBar.max
        val pos = (position * 1.0 / duration * viewBinding.seekBar.max).toInt()
        viewBinding.seekBar.progress = pos
        viewBinding.currentDuration.text = stringForTime(position)
        viewBinding.duration.text = stringForTime(duration)
    }

}