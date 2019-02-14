package com.qj.client.iter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;

/**
 * author qujun
 * des DownSubscriber
 * time 2019/2/13 22:01
 * Because had because, so had so, since has become since, why say why。
 **/

public class DownSubscriber<ResponseBody> implements Observer<ResponseBody> {


    private String mFilePath = "";

    private String mFileName = "";

    private JYCallBack mCallBack = null;

    private Handler handler;

    public DownSubscriber(String filePath, String fileName, JYCallBack callBack) {
        mFilePath = filePath;
        mFileName = fileName;
        mCallBack = callBack;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mCallBack != null)
            mCallBack.onStart();
    }

    @Override
    public void onNext(ResponseBody responseBody) {

        okhttp3.ResponseBody body = (okhttp3.ResponseBody) responseBody;

        try {

            String fileFullPath = mFilePath + File.separator + mFileName;

            File futureStudioIconFile = new File(fileFullPath);

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                Log.d(TAG, "file length: " + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    handler = new Handler(Looper.getMainLooper());
                    final long finalFileSizeDownloaded = fileSizeDownloaded;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallBack != null)
                                mCallBack.onProgress(finalFileSizeDownloaded, fileSize);
                        }
                    });
                }

                outputStream.flush();

                Log.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);


            } catch (IOException e) {
                onError(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mCallBack != null)
            mCallBack.onError(e);
    }

    @Override
    public void onComplete() {
        if (mCallBack != null)
            mCallBack.onComplete();
    }
}
