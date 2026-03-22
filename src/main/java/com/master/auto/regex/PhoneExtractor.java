package com.master.auto.regex;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneExtractor {
    private static final String REGEX = "(010|02|0[3-6][1-9])-?(\\d{3,4})-?(\\d{4})";

    public Set<String> extract(String text) {
        Set<String> phoneNumbers = new HashSet<>();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            phoneNumbers.add(matcher.group());
        }
        return phoneNumbers;
    }
}
