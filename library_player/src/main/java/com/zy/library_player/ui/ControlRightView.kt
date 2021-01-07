package com.zy.library_player.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.zy.library_player.ControlDelegate
import com.zy.library_player.controll.ControlComponent
import com.zy.library_player.databinding.ControlBottomViewBinding
import com.zy.library_player.databinding.ControlRightViewBinding
import com.zy.library_player.status.PlayStatus

/**
 * @ProjectName: YPlayer
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/6 11:20
 */
class ControlRightView : FrameLayout, ControlComponent {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val viewBinding = ControlRightViewBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var controlDelegate: ControlDelegate

    init {
        setOnClickListener {
            val playStatus = controlDelegate.getPlayStatus()
            if (playStatus == PlayStatus.PLAYING) {
                controlDelegate.pause()
            } else {
                controlDelegate.start()
            }
        }
    }

    override fun attach(controlDelegate: ControlDelegate) {
        this.controlDelegate = controlDelegate
    }
}