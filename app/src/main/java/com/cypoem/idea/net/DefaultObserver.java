package com.cypoem.idea.net;

import android.widget.Toast;
import com.airong.core.BaseRxActivity;
import com.airong.core.utils.LogUtils;
import com.airong.core.utils.ToastUtils;
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
        mActivity.showProgress("");
    }

    public DefaultObserver(BaseRxActivity activity, boolean isAddInStop) {
        this.isAddInStop = isAddInStop;
        mActivity = activity;
        mActivity.showProgress("");
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
        // TODO 根据后台返回数据进行配置
        onSuccess(response);
        /*switch (response.getStatus()) {
            case STATUS_OK:
                onSuccess(response);
                break;
            case STATUS_FAIL:
                onFail(response);
                break;
            case STATUS_ERROR:
            default:
        }*/
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onNetWorkFail(BAD_NETWORK);
            return;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onFail(PARSE_ERROR);
            return;
        } else if (e instanceof ConnectException) {   //   连接错误
            onNetWorkFail(CONNECT_ERROR);
            return;
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onNetWorkFail(CONNECT_TIMEOUT);
        } else {
            onFail(UNKNOWN_ERROR);
            return;
        }
        /*else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setDisplayMessage(resultException.getMsg());
            return ex;
        }*/
    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     */
    abstract public void onSuccess(T response);

    public void onNetWorkFail(DefaultObserver.FailReason reason){
        mActivity.dismissProgress();
        switch (reason){
            case CONNECT_ERROR:
                ToastUtils.show("网络连接失败", Toast.LENGTH_SHORT);
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.show("网络连接超时", Toast.LENGTH_SHORT);
                break;
            case BAD_NETWORK:
                ToastUtils.show("服务器异常", Toast.LENGTH_SHORT);
                break;
        }
    }

    /**
     * 请求失败
     * @param reason
     */
    public void onFail(DefaultObserver.FailReason reason) {
        mActivity.dismissProgress();
        switch (reason) {
            case PARSE_ERROR:
                ToastUtils.show("数据解析错误", Toast.LENGTH_SHORT);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.show("未知错误", Toast.LENGTH_SHORT);
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
