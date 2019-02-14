package com.qj.retrofit;

import java.util.List;

/**
 * @author qujun
 * @des
 * @time 2019/2/1 13:56
 * Because had because, so had so, since has become since, why say why。
 **/

public class WanInfo {

    private List<WanSubInfo> datas = null;

    private  int curPage = 0;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<WanSubInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<WanSubInfo> datas) {
        this.datas = datas;
    }
}
