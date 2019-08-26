package com.haijie.utilslibrary.internet.okhttp;

import android.os.Environment;

import com.haijie.utilslibrary.internet.listener.OnInternetCallbackListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpHelper {

    private static OKHttpHelper mInstance;
    private static OkHttpClient mOkHttpClient;
    private OnInternetCallbackListener mCallbackListener;

    private OKHttpHelper() {
        File sdcache = new File(Environment.getExternalStorageDirectory(), "okhttpcache");
        int cacheSize = 10 * 1024 * 1024;
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.MINUTES)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                .build();
    }

    public static OKHttpHelper getInstance() {
        if (null == mInstance) {
            synchronized (OKHttpHelper.class) {
                if (null == mInstance) {
                    mInstance = new OKHttpHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 设置回调接口
     *
     * @param callbackListener 回调接口
     * @return 实例对象
     */
    public OKHttpHelper setPermissionCallbackListener(OnInternetCallbackListener callbackListener) {
        mCallbackListener = callbackListener;
        return mInstance;
    }

    /**
     * get请求方式
     *
     * @param url 请求地址
     */
    public void doGet(String url) {
        //创建Request
        Request request = new Request.Builder().url(url).build();
        //得到Call
        Call call = mOkHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mCallbackListener.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mCallbackListener.onResponse();
            }
        });
    }

    /**
     * post请求方式
     *
     * @param url    请求地址
     * @param params 请求体
     */
    public void doPost(String url, Map<String, String> params) {
        //3.x版本post请求换成FormBody 封装键值对参数
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合给请求体添加值
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        //创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        //获取call
        Call call = mOkHttpClient.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mCallbackListener.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mCallbackListener.onResponse();
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url      接口地址
     * @param file     文件
     * @param fileName 文件名
     */
    public void uploadFile(String url, File file, String fileName) {
        //创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mCallbackListener.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mCallbackListener.onResponse();
            }
        });
    }

    /**
     * post请求上传图片
     * 参数1 url
     */
    public void uploadPic(String url, Map<String, Object> params) {
        // 创建MultipartBody.Builder
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof File) {
                File file = (File) value;
                //创建RequestBody 封装file参数
                builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            } else {
                builder.addFormDataPart(entry.getKey(), value.toString());
            }
        }
        //创建RequestBody 设置类型等
        RequestBody requestBody = builder.build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();
        //得到Call
        Call call = mOkHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mCallbackListener.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mCallbackListener.onResponse();
            }
        });
    }

    /**
     * 上传json字符
     *
     * @param url        接口地址
     * @param jsonParams Json串
     */
    public void doPostJson(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mCallbackListener.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mCallbackListener.onResponse();
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url     下载地址
     * @param saveDir 保存的位置
     */
    public void downFile(final String url, final String saveDir) {
        //创建Request
        Request request = new Request.Builder().url(url).build();
        //创建Call
        Call call = mOkHttpClient.newCall(request);
        //同步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mCallbackListener.onFailure();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    //保存路径
                    final String fileDir = isExistDir(saveDir);
                    //文件
                    File file = new File(fileDir, getNameFromUrl(url));

                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    mCallbackListener.onResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            return savePath;
        }
        return null;
    }
}
