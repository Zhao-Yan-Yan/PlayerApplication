package com.zy.library_player

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.zy.library_player.controll.ControlComponent
import com.zy.library_player.databinding.ControlBaseBinding
import com.zy.library_player.status.PlayStatus

class BaseController : FrameLayout, ViewControl, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnTouchListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val viewBinding = ControlBaseBinding.inflate(LayoutInflater.from(context), this, true)
    private val controlComponents = mutableListOf<ControlComponent>()
    private lateinit var controlDelegate: ControlDelegate

    init {
        add(
            viewBinding.controlLeft,
            viewBinding.controlTop,
            viewBinding.controlRight,
            viewBinding.controlBottom
        )
    }

    fun attachDelegate(controlDelegate: ControlDelegate) {
        this.controlDelegate = controlDelegate
    }

    override fun startProgress() {
    }

    override fun stopProgress() {
    }

    private fun add(vararg controlComponent: ControlComponent) {
        controlComponent.forEach {
            controlComponents.add(it)
            it.attach(controlDelegate)
        }
    }

    fun handleDurationChanged(duration: Long, position: Long) {
        controlComponents.forEach {
            it.progressChanged(duration, position)
        }
    }

    fun handlePlayStatusChanged(status: PlayStatus) {
        controlComponents.forEach {
            it.playStateChanged(status)
        }
    }

    private fun handleSetProgress(duration: Long, position: Long) {
        controlComponents.forEach {
            it.progressChanged(duration, position)
        }
    }


    //Guest start
    override fun onDown(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onShowPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLongPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
    //Guest end

}