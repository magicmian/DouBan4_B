package com.example.douban.douban4_b.http;

/**
 * Created by wangm on 2017/2/22.
 */

public class HttpStatus {
    public int getCode() {
        return mCode;
    }

    public void setCode(int mCode) {
        this.mCode = mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    private int mCode;

    private String mMessage;


    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */

    public boolean isCodeInvalid() {
        return mCode != Constants.WEB_RESP_CODE_SUCCESS;
    }

}
