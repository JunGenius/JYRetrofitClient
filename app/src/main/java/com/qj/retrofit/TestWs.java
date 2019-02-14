package com.qj.retrofit;

import com.qj.client.ws.BaseWebService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qujun
 * @des
 * @time 2019/2/14 15:51
 * Because had because, so had so, since has become since, why say why。
 **/

public class TestWs extends BaseWebService {

    private String byProvinceName = "";

    public TestWs(String byProvinceName) {
        this.byProvinceName = byProvinceName;
    }

    @Override
    public Map<String, String> setParamMap() {

        Map<String, String> map = new HashMap<>();
        map.put("byProvinceName", byProvinceName); // 查询参数
        return map;
    }

    @Override
    public String setMethod() {
        return "getSupportCity"; // 查询方法
    }
}
