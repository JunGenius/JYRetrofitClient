# QYRetrofitFrame
Android基于Retrofit2.0+RxJava 网络请求框架

参考文章:https://www.jianshu.com/p/29c2a9ac5abf

1.     
       // 配置网络   其他api参考文章..
       new JYRetrofitConfigManager.Builder().setBASE_URL("http://www.wanandroid.com/").build();

       // 配置webservice
       new WebServiceConfigManager.Builder()

                .setBASE_URL("http://www.webxml.com.cn/WebServices/") // 请求地址

                .setNAME_SPACE("http://WebXml.com.cn/") // 命名空间

                .setHEAD_PAGE("WeatherWebService.asmx") // 页面

                .build();


2. Get请求

    
    
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
    
3.POST 请求

   
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
    
    
4.文件上传
  
 
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
    
5.多个文件上传
    
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
    
    
    
6.文件下载
    
   
   
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
        
        
7.自定义访问接口



      private void testCustomApi() {

              MyApiService service = JYRetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);

              // execute and add observable to RxJava
              JYRetrofitClient.getInstance(MainActivity.this).execute(
                      service.getData("60"), new Observer<WanAndroidInfo>() {
                          @Override
                          public void onSubscribe(Disposable d) {

                          }

                          @Override
                          public void onNext(WanAndroidInfo info) {

                          }

                          @Override
                          public void onError(Throwable e) {

                          }

                          @Override
                          public void onComplete() {

                          }
                      });
          }
    
    
    
    
    
8.改变Host地址 访问自定义接口

      private void testHostCustomApi() {

              MyApiService service = JYRetrofitClient.getInstance(MainActivity.this , "http://xxxxxxx").create(MyApiService.class);

              // execute and add observable to RxJava
              JYRetrofitClient.getInstance(MainActivity.this, "http://xxxxxxx").execute(
                      service.getData("60"), new Observer<WanAndroidInfo>() {
                          @Override
                          public void onSubscribe(Disposable d) {

                          }

                          @Override
                          public void onNext(WanAndroidInfo info) {

                          }

                          @Override
                          public void onError(Throwable e) {

                          }

                          @Override
                          public void onComplete() {

                          }
                      });
          }




9. 访问webservice
    
    
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
