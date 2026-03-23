package com.master.auto.regex;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneExtractor {
    // 🎯 1. 정규표현식: 01로 시작하는 유효한 휴대폰 번호 체계만 타겟팅
    private static final String REGEX = "(010|011|016|017|018|019)-?(\\d{3,4})-?(\\d{4})";

    public Set<String> extract(String text) {
        Set<String> phoneNumbers = new HashSet<>();
        if (text == null || text.isEmpty()) return phoneNumbers;

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String rawNum = matcher.group();
            // 숫자만 남기기 '딸깍'
            String cleanNum = rawNum.replaceAll("[^0-9]", "");

            // 🎯 2. 무결성 필터 강화
            // - 전체 길이가 10자리 혹은 11자리여야 함
            // - 반드시 우리가 정의한 유효한 시작 번호(010, 011 등)로 시작해야 함
            if (isValidLength(cleanNum) && isValidPrefix(cleanNum)) {
                phoneNumbers.add(formatNumber(cleanNum));
            }
        }
        return phoneNumbers;
    }

    // 유효한 휴대폰 번호 시작인지 체크 (01-0060 같은 노이즈 차단)
    private boolean isValidPrefix(String num) {
        return num.startsWith("010") || num.startsWith("011") ||
                num.startsWith("016") || num.startsWith("017") ||
                num.startsWith("018") || num.startsWith("019");
    }

    private boolean isValidLength(String num) {
        return num.length() == 10 || num.length() == 11;
    }

    // 🎯 3. 포맷팅 최적화 (휴대폰 번호 전용 가공)
    private String formatNumber(String num) {
        if (num.length() == 11) {
            // 010-1234-5678
            return num.substring(0, 3) + "-" + num.substring(3, 7) + "-" + num.substring(7);
        } else if (num.length() == 10) {
            // 011-123-4567 (기존 02 지역번호 로직에서 휴대폰 최적화로 변경)
            return num.substring(0, 3) + "-" + num.substring(3, 6) + "-" + num.substring(6);
        }
        return num;
    }
}