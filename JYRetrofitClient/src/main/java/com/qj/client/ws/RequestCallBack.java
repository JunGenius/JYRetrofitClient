package com.qj.client.ws;


/**
 * author qujun (Deprecated)
 * time 2019/1/21 11:13
 * Because had because, so had so, since has become since, why say why。
 **/

public interface RequestCallBack{

    void onSuccess(String data);

    void onError(int result, String msg);

    void onStart();

    void onFinish();
}
