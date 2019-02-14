package com.qj.client.net;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.qj.client.iter.DownSubscriber;
import com.qj.client.iter.JYCallBack;
import com.qj.client.iter.UploadSubscriber;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author qujun
 * des JYRetrofitClient
 * time 2019/2/13 22:56
 * Because had because, so had so, since has become since, why say why。
 **/


public class JYRetrofitClient {

    private static final int DEFAULT_TIMEOUT = JYRetrofitConfigManager.getBuilder().CONNECT_TIMEOUT;
    private BaseApiService apiService;
    private static OkHttpClient okHttpClient;
    private static String baseUrl = JYRetrofitConfigManager.getBuilder().BASE_URL;
    //        private static Context mContext;
//    private Context mContext;
    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;
    private static JYRetrofitClient INSTANCE = null;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(
                            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


//    private static class SingletonHolder {
//
//        private static JYRetrofitClient INSTANCE = new JYRetrofitClient(
//                mContext);
//
//    }

    public static JYRetrofitClient getInstance(Context context) {
//        if (context != null) {
//            mContext = context;
//        }
//        return SingletonHolder.INSTANCE;

        if (INSTANCE != null)
            return INSTANCE;

        INSTANCE = new JYRetrofitClient(
                context);

        return INSTANCE;
    }

    public static JYRetrofitClient getInstance(Context context, String url) {
        if (INSTANCE != null)
            return INSTANCE;

        INSTANCE = new JYRetrofitClient(context, url);

        return INSTANCE;
    }

    public static JYRetrofitClient getInstance(Context context, String url, Map<String, String> headers) {

        if (INSTANCE != null)
            return INSTANCE;

        INSTANCE = new JYRetrofitClient(context, url, headers);

        return INSTANCE;
    }

    private JYRetrofitClient() {

    }

    private JYRetrofitClient(Context context) {

        this(context, baseUrl, null);
    }

    private JYRetrofitClient(Context context, String url) {

        this(context, url, null);
    }

    private JYRetrofitClient(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), JYRetrofitConfigManager.getBuilder().CACHE_PATH);
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, JYRetrofitConfigManager.getBuilder().CACHE_SIZE);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new NovateCookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CaheInterceptor(context))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(JYRetrofitConfigManager.getBuilder().MAX_IDLE_CONNECTIONS, JYRetrofitConfigManager.getBuilder().KEEP_ALIVE_DURATION, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .retryOnConnectionFailure(true)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * ApiBaseUrl
     *
     * @param newApiBaseUrl
     */
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        baseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * addcookieJar
     */
    public static void addCookie(Context context) {
        okHttpClient.newBuilder().cookieJar(new NovateCookieManger(context)).build();
        retrofit = builder.client(okHttpClient).build();
    }

    /**
     * ApiBaseUrl
     *
     * @param newApiHeaders
     */
    public static void changeApiHeader(Map<String, String> newApiHeaders) {

        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
        builder.client(httpClient.build()).build();
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public JYRetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }

        if (baseUrl.equals("")) {
            throw new RuntimeException("baseUrl is null!");
        }

        return retrofit.create(service);
    }

    public void get(String url, Map<String, String> parameters, JYCallBack callBack) {

        apiService.executeGet(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new BaseSubscriber(callBack));
    }

    public void post(String url, Map<String, String> parameters, JYCallBack callBack) {

        apiService.executePost(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new BaseSubscriber(callBack));
    }

    public void json(String url, RequestBody jsonStr, JYCallBack callBack) {

        apiService.json(url, jsonStr)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new BaseSubscriber(callBack));
    }

    public void uploadFile(String url, File file, JYCallBack callBack) {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        FileRequestBody requestBody = new FileRequestBody(requestFile, callBack);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        apiService.upLoadFile(url, body)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new UploadSubscriber(callBack));
    }


    public void uploadFiles(String url, List<File> files, JYCallBack callback) {

        Map<String, RequestBody> map = new HashMap<>();

        for (File file : files) {

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            FileRequestBody requestBody = new FileRequestBody(requestFile, callback);

            map.put("file\"; filename=\"" + file.getName(), requestBody);
        }

        apiService.upLoadFiles(url, map)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new UploadSubscriber(callback));
    }


    public void download(String url, String filePath, String fileName, JYCallBack callback) {
        apiService.downloadFile(url)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new DownSubscriber(filePath, fileName, callback));
    }


    private ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer();
    }

    public <T> ObservableTransformer transformer() {

        return new ObservableTransformer() {

            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {

        @Override
        public Observable<T> apply(Throwable throwable) {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    private class HandleFuc<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(BaseResponse<T> response) {
            if (!response.isOk())
                throw new RuntimeException(response.getMsg());
            return response.getData();
        }
    }


    public static <T> void execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
