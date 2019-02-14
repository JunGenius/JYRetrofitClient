package com.qj.client.net;


import java.util.Map;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * des  基类API
 * author qujun
 * time 2019/2/13 22:49
 * Because had because, so had so, since has become since, why say why。
 **/

public interface BaseApiService {


    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path(value = "url", encoded = true) String url,
            @QueryMap Map<String, String> maps
    );


    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path(value = "url", encoded = true) String url,
            @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(
            @Path(value = "url", encoded = true) String url,
            @Body RequestBody jsonStr);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path(value = "url", encoded = true) String url,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFiles(
            @Path(value = "url", encoded = true) String url,
            @PartMap() Map<String, RequestBody> maps);


    @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path(value = "url", encoded = true) String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
