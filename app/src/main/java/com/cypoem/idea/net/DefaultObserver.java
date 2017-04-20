package com.cypoem.idea.net;

import android.widget.Toast;

import com.airong.core.BaseRxActivity;
import com.airong.core.utils.LogUtils;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.cypoem.idea.net.DefaultObserver.FailReason.BAD_NETWORK;
import static com.cypoem.idea.net.DefaultObserver.FailReason.CONNECT_ERROR;
import static com.cypoem.idea.net.DefaultObserver.FailReason.CONNECT_TIMEOUT;
import static com.cypoem.idea.net.DefaultObserver.FailReason.PARSE_ERROR;
import static com.cypoem.idea.net.DefaultObserver.FailReason.UNKNOWN_ERROR;

/**
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {

    private BaseRxActivity mActivity;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;

    public DefaultObserver(BaseRxActivity activity) {
        mActivity = activity;
        mActivity.showProgress();
    }

    public DefaultObserver(BaseRxActivity activity, boolean isAddInStop) {
        this.isAddInStop = isAddInStop;
        mActivity = activity;
        mActivity.showProgress();
    }

    public DefaultObserver(BaseRxActivity activity, boolean isAddInStop, boolean isShowLoading) {
        this.isAddInStop = isAddInStop;
        mActivity = activity;
        if (isShowLoading)
            mActivity.showProgress();
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isAddInStop) {    //  在onStop中取消订阅
            mActivity.addRxStop(d);
        } else { //  在onDestroy中取消订阅
            mActivity.addRxDestroy(d);
        }
    }

    @Override
    public void onNext(T response) {
        mActivity.dismissProgress();
        if (response.getCode() == 200) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());

        mActivity.dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onError(BAD_NETWORK);
        } else if (e instanceof ConnectException) {   //   连接错误
            onError(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onError(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onError(PARSE_ERROR);
        } else {
            onError(UNKNOWN_ERROR);
        }
        /*else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setDisplayMessage(resultException.getMsg());
            return ex;
        }*/
    }

    @Override
    public void onComplete() {}

    /**
     * 请求成功
     * @param response  服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     *  请求失败
     * @param response  服务器返回的数据
     */
     public void onFail(T response){
         ToastUtils.show(response.getMessage(), Toast.LENGTH_SHORT);
     }

    /**
     *  请求异常
     * @param reason
     */
    public void onError(DefaultObserver.FailReason reason) {
        mActivity.dismissProgress();
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show(R.string.connect_error, Toast.LENGTH_SHORT);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show(R.string.connect_timeout, Toast.LENGTH_SHORT);
                break;

            case BAD_NETWORK:
                ToastUtils.show(R.string.bad_network, Toast.LENGTH_SHORT);
                break;

            case PARSE_ERROR:
                ToastUtils.show(R.string.parse_error, Toast.LENGTH_SHORT);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.show(R.string.unknown_error, Toast.LENGTH_SHORT);
                break;
        }
    }


    public enum FailReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,

        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
