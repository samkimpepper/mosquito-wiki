package com.mosquito.mosquitowiki.utils;

public class SlugUtil {
    public static String toSlug(String input) {
    return input
            .toLowerCase()
            .trim()
            .replaceAll("[^a-z0-9가-힣\\s-]", "")  // 특수문자 제거
            .replaceAll("\\s+", "-")                 // 공백 → -
            .replaceAll("-+", "-");                  // -- → -
    }
}
