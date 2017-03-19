package com.xiaohui.parser.impl;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.xiaohui.parser.LogUtil;
import com.xiaohui.parser.ReqPrepare;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by xiaohui on 2016/11/26.
 */

public class VolleyTask extends AbstractParserTask implements Response.ErrorListener {

    private static final String TAG = "VolleyTask";

    private static RequestQueue sRequestQueue;

    //Volley请求类
    protected Request mRequest;
    /**
     * 请求方式.默认是NORMAL级别
     */
    private int mReqPriority;

    public VolleyTask(ReqPrepare reqPrepare) {
        super(reqPrepare);
    }

    private <T> void initRequest(final Response.Listener<T> listener, Response.ErrorListener errorListener) {
        LogUtil.i(TAG, "mReqPath:" + mReqPath);
        int volleyMethod = getVolleyMethod();
        //创建一个定制的Request对象
        mRequest = new Request<T>(volleyMethod, mReqPath, errorListener) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {//子线程中
                String dataOfStr = new String(response.data);//HTTP服务器 string类型的返回数据
                LogUtil.i(TAG, "http Content:" + dataOfStr);
                T t = null;
                try {
                    t = (T) JSONObject.parseObject(dataOfStr, mClazz);//fastjson解析对象
                } catch (Exception e) {
                    return Response.error(new VolleyError("JSON解析异常"));
                }
                return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));//返回成功
            }

            @Override
            protected void deliverResponse(T response) {//主线程
                if (listener != null) {
                    listener.onResponse(response);
                }

                if (mTaskListener != null) {
                    if (response != null) {
                        mTaskListener.onTaskSuccess(response);
                    } else {
                        mTaskListener.onTaskFailure(new Exception("解析失败"));
                    }

                    if (mTaskListener != null) {//任务结束的回调，无关乎成功与否
                        mTaskListener.onTaskFinish();
                    }
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mRequestParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mReqHeaders == null ? getDefaultHeaders() : mReqHeaders;
            }

            private Map<String, String> getDefaultHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("User-Agent", "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.93 Safari/537.36");
                return header;
            }

            @Override
            public String getBodyContentType() {
                return mContentType.value();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                byte[] jsonBody = null;
                if (!TextUtils.isEmpty(mContent)) {//如果有直接定制请求体需求，直接返回定制的请求体内容
                    try {
                        jsonBody = mContent.getBytes("UTF-8");
                        return jsonBody;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                return super.getBody();
            }

            @Override
            public String getUrl() {
                String url = super.getUrl();
                if (mReqMethod == RequstMethod.GET) {
                    String param = getEncodeParameters();
                    if (!TextUtils.isEmpty(param)) {//如果有设置参数那么不为空
                        if (url.indexOf("?") == -1) {//如果后面没有?就追加?
                            url = url + "?";
                        } else {
                            url = url + "&";//如果有问号，就追加&号
                        }
                        url += param;//将参数设置到URL上
                    }
                }
                LogUtil.d("lxh", url);
                return url;
            }
        };
        mRequest.setRetryPolicy(new DefaultRetryPolicy(mTimeoutMs, 0, 1f));
    }

    /**
     * 初始化Volley任务
     *
     * @param appCtx
     */
    public static void init(Context appCtx) {
        sRequestQueue = Volley.newRequestQueue(appCtx);
    }

    private RequestQueue getRequestQueue() {
        if (sRequestQueue == null) {
            throw new RuntimeException("Volley request queue not be initialzied!!!");
        }
        return sRequestQueue;
    }

    /**
     * 获得Volley定义的请求方式
     *
     * @return
     */
    private int getVolleyMethod() {
        int volleyMethod = 0;
        switch (mReqMethod) {
            case GET:
                volleyMethod = Request.Method.GET;
            case POST:
                volleyMethod = Request.Method.POST;
        }
        return volleyMethod;
    }

    @Override
    public int getPriority() {
        return mReqPriority;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void reload() {
    }

    /**
     * 获得volley对应的优先级枚举
     *
     * @return
     */
    private Request.Priority getVolleyPriority() {
        if (getPriority() == PRIORITY_LOW) {
            return Request.Priority.LOW;
        } else if (getPriority() == PRIORITY_NORMAL) {
            return Request.Priority.NORMAL;
        } else if (getPriority() == PRIORITY_HIGH) {
            return Request.Priority.HIGH;
        } else if (getPriority() == PRIORITY_IMMEDIATE) {
            return Request.Priority.IMMEDIATE;
        }
        return Request.Priority.NORMAL;
    }

    /**
     * 开启异步JSON解析任务
     */
    public void asyncParse() {
        if (mTaskListener != null) {
            mTaskListener.onTaskStart();
        }
        initRequest(null, this);
        //将request对象添加到请求队列中
        getRequestQueue().add(mRequest);
    }

    @Override
    public <T> T syncParse() {
        T t = null;
        RequestFuture<T> future = RequestFuture.newFuture();
        initRequest(future, future);
        future.setRequest(mRequest);
        getRequestQueue().add(mRequest);
        try {
            t = future.get(mTimeoutMs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LogUtil.w(TAG, "catch InterruptedException throw by sync.", e);
        } catch (ExecutionException e) {
            LogUtil.w(TAG, "catch ExecutionException throw by sync.", e);
        } catch (TimeoutException e) {
            LogUtil.w(TAG, "catch TimeoutException throw by sync.", e);
        }
        return t;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mTaskListener != null) {
            mTaskListener.onTaskFailure(error);
        }

        if (mTaskListener != null) {//任务结束的回调，无关乎成功与否
            mTaskListener.onTaskFinish();
        }
    }

    /**
     * 取消异步任务
     */
    @Override
    public void cancel() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }

    public static class Builder extends AbstractParserTask.Builder {

        /**
         * 构造一个task的builder对象，两个必须填写的参数
         *
         * @param reqPath
         * @param clazz
         */
        public Builder(String reqPath, Class<?> clazz) {
            super(reqPath, clazz);
        }

        @Override
        public AbstractParserTask createTask() {
            return new VolleyTask(reqPrepare);
        }
    }

}
