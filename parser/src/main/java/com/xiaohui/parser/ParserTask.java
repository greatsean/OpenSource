package com.xiaohui.parser;

/**
 * Created by xiaohui on 7/16/16.
 */
public interface ParserTask {

    /**
     * 请求优先级别-低
     */
    public static final int PRIORITY_LOW = 0;

    /**
     * 请求优先级别-普通
     */
    public static final int PRIORITY_NORMAL = 1;

    /**
     * 请求优先级别-高
     */
    public static final int PRIORITY_HIGH = 2;

    /**
     * 请求优先级别-立即
     */
    public static final int PRIORITY_IMMEDIATE = 3;

    /**
     * 获取当前请求优先级
     *
     * @return
     */
    int getPriority();

    /**
     * 解析任务是否正在进行
     *
     * @return
     */
    boolean isRunning();

    /**
     * 重新加载当前的解析任务(暂未实现)
     */
    void reload();

    /**
     * 异步解析任务
     */
    void asyncParse();

    /**
     * 同步解析任务
     */
    <T> T syncParse();

    /**
     * 取消任务
     */
    void cancel();

}
