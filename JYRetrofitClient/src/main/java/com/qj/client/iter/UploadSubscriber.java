package com.qj.client.iter;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * author qujun
 * des UploadSubscriber
 * time 2019/2/13 22:48
 * Because had because, so had so, since has become since, why say why。
 **/
public class UploadSubscriber implements Observer<ResponseBody> {


    private JYCallBack callBack = null;

    public UploadSubscriber(JYCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        if (callBack != null)
            callBack.onNext(responseBody);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (callBack != null)
            callBack.onStart();
    }

    @Override
    public void onComplete() {
        if (callBack != null)
            callBack.onComplete();
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null)
            callBack.onError(e);
    }
}
