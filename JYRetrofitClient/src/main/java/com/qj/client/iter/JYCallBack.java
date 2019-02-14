package com.qj.client.iter;

/**
 * author qujun
 * des
 * time 2019/2/14 10:02
 * Because had because, so had so, since has become since, why say why。
 **/

public abstract class JYCallBack<T> {

    public void onStart() {
    }

    public void onProgress(long contentLength, long bytesTotal) {
    }

    public void onComplete() {
    }

    public abstract void onError(Throwable e);

    public abstract void onNext(T t);
}
