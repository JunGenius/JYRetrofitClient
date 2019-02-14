package com.qj.retrofit;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qj.client.iter.JYCallBack;
import com.qj.client.net.JYRetrofitClient;
import com.qj.client.net.JYRetrofitConfigManager;
import com.qj.client.ws.WebServiceConfigManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {


    private TextView txtInfo = null;

    private Button btnTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = findViewById(R.id.txt_info);

        btnTest = findViewById(R.id.btn_test);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testWs();
            }
        });

        new JYRetrofitConfigManager.Builder().setBASE_URL("http://192.168.2.246:80/").build();

        new WebServiceConfigManager.Builder()

                .setBASE_URL("http://www.webxml.com.cn/WebServices/") // 请求地址

                .setNAME_SPACE("http://WebXml.com.cn/") // 命名空间

                .setHEAD_PAGE("WeatherWebService.asmx") // 页面

                .build();
    }


    private void testGet() {

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("cid", "60");


        JYRetrofitClient.getInstance(this).createBaseApi().get("article/list/0/json", maps, new JYCallBack<WanAndroidInfo>() {
            @Override
            public void onStart() {
                Log.i("JYRetrofitClient", "onStart");
            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("JYRetrofitClient", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("JYRetrofitClient", "onComplete");
            }

            @Override
            public void onNext(WanAndroidInfo wanAndroidInfo) {

            }
        });
    }

    private void testPost() {

        Map<String, String> maps = new HashMap<String, String>();
        maps.put("id", "2334");


        JYRetrofitClient.getInstance(this).createBaseApi().post("lg/uncollect_originId/2333/json", maps, new JYCallBack<WanAndroidInfo>() {
            @Override
            public void onStart() {
                Log.i("JYRetrofitClient", "onStart");
            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("JYRetrofitClient", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("JYRetrofitClient", "onComplete");
            }

            @Override
            public void onNext(WanAndroidInfo wanAndroidInfo) {

            }
        });
    }

    private void testUpload() {

        File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");

        JYRetrofitClient.getInstance(this).createBaseApi().uploadFile("upload", file, new JYCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("UPLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
            }
        });
    }

    private void testUploadFiles() {

        File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory(), "2.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "3.jpg");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file);


        JYRetrofitClient.getInstance(this).createBaseApi().uploadFiles("upload", files, new JYCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("UPLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
                txtInfo.setText((int) ((float) contentLength / bytesTotal * 100) + "");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                txtInfo.setText("Complete");
            }
        });
    }

    private void testDownload() {

        String filePath = Environment.getExternalStorageDirectory().getPath();

        String fileName = "123.jpg";

        JYRetrofitClient.getInstance(this).createBaseApi().download("upload/123.jpg", filePath, fileName, new JYCallBack() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onProgress(long contentLength, long bytesTotal) {
                super.onProgress(contentLength, bytesTotal);
                Log.i("DOWNLOAD", (int) ((float) contentLength / bytesTotal * 100) + "");
                txtInfo.setText((int) ((float) contentLength / bytesTotal * 100) + "");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                txtInfo.setText("Complete");
            }
        });
    }

    private void testWs() {

        TestWs ws = new TestWs("北京");

        ws.getRequest()
                // 处理相应的实体转换
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Exception {
                        return new Object();
                    }
                }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
