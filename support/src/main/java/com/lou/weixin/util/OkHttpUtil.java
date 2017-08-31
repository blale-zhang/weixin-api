package com.lou.weixin.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author loufeng
 * @date 2017/8/29 上午11:02.
 */
public final class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private static final OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS).build();

    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpUtil() {
    }

    /**
     * get 请求
     *
     * @param url url
     * @return 结果
     */
    public static String get(String url) {
        logger.info("start to request {}", url);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * post 方式提交表单数据
     *
     * @param url  url
     * @param json 请求参数
     * @return 结果
     */
    public static String postForm(String url, String json) {
        return postForm(url, json, null);
    }

    /**
     * post 方式提交表单数据
     *
     * @param url     url
     * @param json    请求参数
     * @param headers header
     * @return result
     */
    public static String postForm(String url, String json, Map<String, Object> headers) {
        logger.info("start to post url {}, params: {}", url, json);
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.header(key, String.valueOf(headers.get(key)));
            }
        }

        Request request = requestBuilder.build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String result;
        try {
            result = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 下载图片并上传到oss
     *
     * @param url  url
     * @param json body
     * @return result
     */
    public static InputStream postDownLoad(String url, String json) {
        logger.info("start to post url {}, params: {}", url, json);
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        Request request = requestBuilder.build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.body().byteStream();
    }

    /**
     * 上传文件
     *
     * @param url  url
     * @param file 文件
     * @return 结果
     */
    public static String uploadFile(String url, File file) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);// 根据文件格式封装文件
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("media", file.getName(), requestFile).build();// 初始化请求体对象，设置Content-Type以及文件数据流
        Request request = new Request.Builder().url(url).post(requestBody).build();// 封装OkHttp请求对象，初始化请求参数
        Call newCall = client.newCall(request);
        Response response = newCall.execute();
        return response.body().string();

    }


}