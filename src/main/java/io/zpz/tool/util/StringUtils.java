package io.zpz.tool.util;

public class StringUtils {

    public static String remove(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static boolean isEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

}
