package com.zy.library_player

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 18:23
 */
interface ViewControl {

    /**
     * 开始刷新进度
     */
    fun startProgress()

    /**
     * 停止刷新进度
     */
    fun stopProgress()
}