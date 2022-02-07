package com.example.spb.presenter.utils;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author nmy
 * @title: DataVerificationTool
 * @date 2022-01-25 23:42
 */
public class DataVerificationTool {

    public static boolean isEmpty(String... strs) {
        for (String str : strs) {
            if (str == null || "".equals(str) || str.length() == 0) {
                return true;
            }
            if ("null".equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String isEmpty(Response response) {
        ResponseBody body = response.body();
        String str = null;
        try {
            if (body == null) {
                return null;
            }
            str = body.string();

            if ("".equals(str) || str.length() == 0) {
                return null;
            }
            if ("null".equals(str)) {
                return null;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
        return str;
    }
}
