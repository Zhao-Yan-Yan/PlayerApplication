package com.zy.library_player.status

/**
 * @ProjectName: PlayerApplication
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2021/1/7 17:24
 */
enum class PlayStatus {
    /**
     * 出错
     */
    ERROR,

    /**
     * 空置
     */
    IDLE,

    /**
     * 准备中
     */
    PREPARING,

    /**
     * 准备完成
     */
    PREPARED,

    /**
     * 播放中
     */
    PLAYING,

    /**
     * 暂停
     */
    PAUSED,

    /**
     * 完成
     */
    COMPLETED,

    /**
     * 正在缓冲中
     */
    BUFFERING,

    /**
     * 缓冲完成
     */
    BUFFERED,

    /**
     * 未知
     */
    UN_KNOW
}