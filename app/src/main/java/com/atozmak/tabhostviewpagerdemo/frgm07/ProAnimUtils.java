package com.atozmak.tabhostviewpagerdemo.frgm07;

/**
 * 属性动画常用工具类,
 * 映射函数
 * 和
 * 中值函数.
 */
public class ProAnimUtils {

    //未明？
    // 映射到下一个域
    public static double mapValueFromRangeToRange
    (double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    // 中间值，
    // value<low的话，返回low；
    // value>high的话,返回high。
    // 返回的值必须在等于low或者high或者在两者之间。
    // clamp夹住。
    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }


}
