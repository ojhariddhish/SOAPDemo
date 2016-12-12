package com.dnsoftindia.soapdemo.utils;

import android.text.TextUtils;

import java.util.Date;

/**
 * Created by Ganesha on 12/8/2016.
 */

public class StringUtils {

    public static boolean hasValue(String data) {
        if (data == null || data.length() == 0) {
            return false;
        }
        else {
            if (data.equalsIgnoreCase("null") || data.equalsIgnoreCase("nil")) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasValue(Date date) {
        if (date == null || date.toString().length()==0) {
            return false;
        }

        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    public final static boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

}
