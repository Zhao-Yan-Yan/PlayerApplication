package com.zy.library_player.player

import android.content.Context

/**
 * @ProjectName: YPlayer
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/6 10:14
 */
abstract class PlayerFactory<P : AbstractPlayer> {
    abstract fun createPlayer(context: Context): P
}