package com.example.douban.douban4_b.http;

import com.example.douban.douban4_b.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by wangm on 2017/2/22.
 */

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        String response2 = response;
//        HttpStatus httpStatus = gson.fromJson(response, HttpStatus.class);
//        if (httpStatus.isCodeInvalid()) {
//            value.close();
//            throw new ApiException(httpStatus.getCode(), httpStatus.getMessage());
//        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        if (BuildConfig.DEBUG) {
            InputStream inputStream2 = new ByteArrayInputStream(response2.getBytes());
            Reader copyReader = new InputStreamReader(inputStream2, charset);
            BufferedReader br = new BufferedReader(copyReader);
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\r\n");
            }
            String jsonStr = sb.toString();
            Logger.json(jsonStr);
        }
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }

    }
}
