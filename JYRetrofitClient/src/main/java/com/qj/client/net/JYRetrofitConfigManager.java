package com.qj.client.net;


/**
 * Webservice configuration management  (require relevant parameters before request)
 * author qujun
 * time 2019/1/21 11:13
 * Because had because, so had so, since has become since, why say why。
 **/

public class JYRetrofitConfigManager {


    private static JYRetrofitConfigManager.Builder mBuilder = null;

    public JYRetrofitConfigManager(JYRetrofitConfigManager.Builder builder) {
        mBuilder = builder;
    }

    public static JYRetrofitConfigManager.Builder getBuilder() {
        if (mBuilder == null)
            throw new RuntimeException("Related parameters need to be configured before request..");

        return mBuilder;
    }

    public static class Builder {

        /*
        * BASE_URL
        * */
        public String BASE_URL = "";

        /*
        *  连接超时时间 (默认20s)
        * */
        public int CONNECT_TIMEOUT = 20; // ws 连接超时时间

        /*
        *   读取超时时间(默认20s)
        * */
        public int READ_TIMEOUT = 20;

        /*
        *   写入超时时间(默认20s)
        * */
        public int WRITE_TIMEOUT = 20;

        /*
        *  缓存路径
        * */
        public String CACHE_PATH = "QYRetrofit/cache";

        /*
        *   缓存大小
        * */
        public int CACHE_SIZE = 10 * 1024 * 1024;


        /*
        *   同时连接的个数
        * */
        public int MAX_IDLE_CONNECTIONS = 8;
        /*
        *   同时连接时间
        * */
        public long KEEP_ALIVE_DURATION = 15;


        public Builder() {
        }


        public Builder setBASE_URL(String BASE_URL) {
            this.BASE_URL = BASE_URL;
            return this;
        }


        public Builder setCONNECT_TIMEOUT(int CONNECT_TIMEOUT) {
            this.CONNECT_TIMEOUT = CONNECT_TIMEOUT;
            return this;
        }

        public Builder setCACHE_PATH(String CACHE_PATH) {
            this.CACHE_PATH = CACHE_PATH;
            return this;
        }

        public Builder setREAD_TIMEOUT(int READ_TIMEOUT) {
            this.READ_TIMEOUT = READ_TIMEOUT;
            return this;
        }

        public Builder setWRITE_TIMEOUT(int WRITE_TIMEOUT) {
            this.WRITE_TIMEOUT = WRITE_TIMEOUT;
            return this;
        }

        public Builder setCACHE_SIZE(int CACHE_SIZE) {
            this.CACHE_SIZE = CACHE_SIZE;
            return this;
        }

        public Builder setMAX_IDLE_CONNECTIONS(int MAX_IDLE_CONNECTIONS) {
            this.MAX_IDLE_CONNECTIONS = MAX_IDLE_CONNECTIONS;
            return this;
        }

        public Builder setKEEP_ALIVE_DURATION(long KEEP_ALIVE_DURATION) {
            this.KEEP_ALIVE_DURATION = KEEP_ALIVE_DURATION;
            return this;
        }

        public JYRetrofitConfigManager build() {
            return new JYRetrofitConfigManager(this);
        }
    }
}

