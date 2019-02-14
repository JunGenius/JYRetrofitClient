package com.qj.client.net;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qj.client.iter.JYCallBack;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * BaseSubscriber
 */

public class BaseSubscriber implements Observer<ResponseBody> {

    //    private Context context;
    private boolean isNeedCahe;

    private JYCallBack callBack = null;

    public BaseSubscriber(JYCallBack callBack) {
//        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public void onNext(ResponseBody t) {
        ParameterizedType type = (ParameterizedType) callBack.getClass().getGenericSuperclass();
        if (type != null) {
            Type[] actualTypeArguments = type.getActualTypeArguments();
            Class tClass = (Class) actualTypeArguments[0];

            Gson gson = new GsonBuilder().create();

            try {
                if (callBack != null)
                    callBack.onNext(gson.fromJson(t.string(), tClass));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("BaseSubscriber", e.getMessage());

        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (callBack != null)
            callBack.onStart();
    }

    @Override
    public void onComplete() {
        Log.i("BaseSubscriber", "http is Complete");
        if (callBack != null)
            callBack.onComplete();
    }

    private void onError(ExceptionHandle.ResponeThrowable e) {
        if (callBack != null)
            callBack.onError(e);
    }

}
