package com.zy.playerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zy.library_player.VideoView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<VideoView>(R.id.player)
        view.play("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4")
    }
}