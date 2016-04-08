package com.atozmak.tabhostviewpagerdemo.mainActivity;

/**
 * Created by Mak on 2016/4/8.
 */
public class LogUtils {

    public static final String PREFIX = "Tabhost..._";

    public static String makLogTag(String str) {
        return PREFIX + str;
    }

    public static String makLogTag(Class cls) {
        return makLogTag(cls.getSimpleName());
    }


}
