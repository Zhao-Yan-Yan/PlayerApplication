package com.zy.library_player.player.ali

import android.content.Context
import com.zy.library_player.player.PlayerFactory

/**
 * @ProjectName: YPlayer
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/6 11:52
 */
class MyAliPlayerFactory : PlayerFactory<MyAliPlayer>() {
    override fun createPlayer(context: Context): MyAliPlayer {
        return MyAliPlayer(context)
    }

    companion object {
        fun create() = MyAliPlayerFactory()
    }
}