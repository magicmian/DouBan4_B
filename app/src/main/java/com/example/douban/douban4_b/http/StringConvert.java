package com.example.douban.douban4_b.http;

import java.io.IOException;

import retrofit2.Converter;

;

/**
 * Created by wangm on 2017/4/8.
 */

public class StringConvert implements Converter {


    public StringConvert() {
    }

    @Override
    public Object convert(Object value) throws IOException {
        return value.toString();
    }
}
