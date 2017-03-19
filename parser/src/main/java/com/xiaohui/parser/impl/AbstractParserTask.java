package com.xiaohui.parser.impl;

import com.xiaohui.parser.LogUtil;
import com.xiaohui.parser.ParserTask;
import com.xiaohui.parser.ReqPrepare;
import com.xiaohui.parser.TaskStatusListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/5.
 * <p/>
 * 通用的异步任务请求类
 */
public abstract class AbstractParserTask implements ParserTask {

    private static final String TAG = "AbstractParserTask";

    //请求路径
    protected String mReqPath;
    //请求返回的模板
    protected Class<?> mClazz;
    //请求参数
    protected Map<String, String> mRequestParams = new HashMap<>();
    //请求方法方式（默认get方式）
    protected RequstMethod mReqMethod = AbstractParserTask.RequstMethod.GET;
    //请求状态监听
    protected TaskStatusListener mTaskListener;
    //请求头
    protected Map<String, String> mReqHeaders = new HashMap<>();
    //内容格式(默认是Form表单方式提交参数)
    protected AbstractParserTask.ContentType mContentType = AbstractParserTask.ContentType.FORM;
    //内容
    protected String mContent;
    /*任务超时时间（毫秒单位）*/
    public int mTimeoutMs;

    /**
     * builder的方式构造task对象
     *
     * @param reqPrepare
     */
    public AbstractParserTask(ReqPrepare reqPrepare) {
        mReqPath = reqPrepare.reqPath;
        mClazz = reqPrepare.clazz;
        mReqMethod = reqPrepare.requstMethod;
        mRequestParams = reqPrepare.reqParams;
        mTaskListener = reqPrepare.listener;
        mReqHeaders = reqPrepare.reqHeaders;
        mReqMethod = reqPrepare.requstMethod;
        mContentType = reqPrepare.contentType;
        mContent = reqPrepare.content;
        mTimeoutMs = reqPrepare.timeoutMs;
    }

    /**
     * @param url    请求地址
     * @param entity 请求的返回的实体字节码对象
     * @deprecated 用Builder方式创建
     * 部分参数构造方法
     */
    public AbstractParserTask(String url, Class<?> entity) {
        this(url, entity, null, RequstMethod.GET);
    }

    /**
     * @param url           请求路径
     * @param entity        请求的实体字节码对象
     * @param requestParams 请求参数
     * @param method        设置请求方式
     * @deprecated 用Builder方式创建
     * 具体的参数构造方法
     */
    public AbstractParserTask(String url, Class<?> entity, Map<String, String> requestParams, RequstMethod method) {
        this.mReqPath = url;
        this.mClazz = entity;
        this.mRequestParams = requestParams;
        this.mReqMethod = method;
        LogUtil.i(TAG, "mRequestParams:" + requestParams);

    }

    public TaskStatusListener getTaskListener() {
        return mTaskListener;
    }

    /**
     * 对外提供设置请求结果的回调监听
     *
     * @param taskListener
     */
    public void setTaskListener(TaskStatusListener taskListener) {
        this.mTaskListener = taskListener;
    }

    /**
     * 任务创建者类
     */
    public static abstract class Builder {
        //请求准备对象
        protected ReqPrepare reqPrepare;

        /**
         * 构造一个task的builder对象，两个必须填写的参数
         *
         * @param reqPath
         * @param clazz
         */
        public Builder(String reqPath, Class<?> clazz) {
            reqPrepare = new ReqPrepare(clazz, reqPath);
        }


        /**
         * 放置参数
         *
         * @param key
         * @param value String类型
         * @return
         */
        public Builder putParams(String key, String value) {
            reqPrepare.reqParams.put(key, value);
            return this;
        }

        /**
         * 设置参数（覆盖之前的所有的参数）
         *
         * @param params
         * @return
         */
        public Builder setParams(Map<String, String> params) {
            reqPrepare.reqParams = params;
            return this;
        }

        /**
         * 放置参数
         *
         * @param key
         * @param value long类型
         * @return
         */
        public Builder putParams(String key, long value) {
            putParams(key, String.valueOf(value));
            return this;
        }

        /**
         * 放置请求头信息
         *
         * @param key
         * @param value
         * @return
         */
        public Builder putHeader(String key, String value) {
            reqPrepare.reqHeaders.put(key, value);
            return this;
        }

        /**
         * 配置请求方式（默认GET请求）
         *
         * @param rm
         * @return
         */
        public Builder setMethod(RequstMethod rm) {
            reqPrepare.requstMethod = rm;
            return this;
        }

        /**
         * 设置请求内容的类型（默认FORM表单）
         *
         * @param ct
         * @return
         */
        public Builder setContentType(ContentType ct) {
            reqPrepare.contentType = ct;
            return this;
        }

        /**
         * 设置请求内容（写在body中的）
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            reqPrepare.content = content;
            return this;
        }

        /**
         * 设置任务监听器
         *
         * @param listener
         * @return
         */
        public Builder setTaskStatusListener(TaskStatusListener<?> listener) {
            reqPrepare.listener = listener;
            return this;
        }

        /**
         * 设置超时时间秒
         *
         * @param timeoutMs
         * @return
         */
        public Builder setTimeoutMs(int timeoutMs) {
            reqPrepare.timeoutMs = timeoutMs;
            return this;
        }

        /**
         * 创建请求任务
         */
        public abstract AbstractParserTask createTask();

    }


    /**
     * Created by xiaohui on 16/7/7.
     * 内容的类型
     */
    public enum ContentType {
        FORM {
            @Override
            String value() {
                //Form表单格式数据
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        }, JSON {
            @Override
            String value() {//JSON格式数据
                return "application/json; charset=UTF-8";
            }
        }, XML {
            @Override
            String value() {//xml格式数据
                return "application/xml; charset=UTF-8";
            }
        };

        abstract String value();
    }

    /**
     * 请求方式
     */
    public enum RequstMethod {
        GET,

        POST,
    }
}
