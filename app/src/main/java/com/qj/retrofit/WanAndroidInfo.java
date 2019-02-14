package com.qj.retrofit;

/**
 * @author qujun
 * @des
 * @time 2019/2/1 13:51
 * Because had because, so had so, since has become since, why say why。
 **/

public class WanAndroidInfo {

    private WanInfo data = null;

    private int errorCode = 0;

    private String errorMsg = "";

    public WanInfo getData() {
        return data;
    }

    public void setData(WanInfo data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
