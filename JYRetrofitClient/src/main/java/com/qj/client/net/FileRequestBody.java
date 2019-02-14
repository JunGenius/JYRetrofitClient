package com.qj.client.net;

import android.os.Handler;
import android.os.Looper;

import com.qj.client.iter.JYCallBack;
import com.qj.client.iter.UploadSubscriber;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * author qujun
 * des FileRequestBody 支持进度
 * time 2019/2/1 16:18
 * Because had because, so had so, since has become since, why say why。
 **/

public final class FileRequestBody extends RequestBody {
    /**
     * 实际请求体
     */
    private RequestBody requestBody;
    /**
     * 上传回调接口
     */
    private JYCallBack callback;
    /**
     * 包装完成的BufferedSink
     */
    private BufferedSink bufferedSink;

    public FileRequestBody(RequestBody requestBody, JYCallBack callback) {
        super();
        this.requestBody = requestBody;
        this.callback = callback;
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
//        if (bufferedSink == null) {
////包装
//            bufferedSink = Okio.buffer(sink(sink));
//        }

//        BufferedSink bufferedSink = Okio.buffer(sink(sink));

        if (sink instanceof Buffer) {
            // Log Interceptor
            requestBody.writeTo(sink);
            return;
        }
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
//写入
        requestBody.writeTo(bufferedSink);
//必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                if (contentLength != 0)

                    handler.post(new ProgressUpdater(bytesWritten, contentLength));
            }
        };
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            callback.onProgress(mUploaded, mTotal);
        }
    }
}
