package com.master.auto;

import com.master.auto.ocr.OcrEngine;
import com.master.auto.regex.PhoneExtractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        OcrEngine engine = new OcrEngine();
        PhoneExtractor extractor = new PhoneExtractor();

        File folder = new File("./test");
        File[] files = folder.listFiles();

        Set<String> finalTmList = new HashSet<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String rawText = engine.scanAny(file);
                        finalTmList.addAll(extractor.extract(rawText)); // 중복 없이 계속 추가 '딸깍'
                    } catch (Exception e) {
                        System.err.println(file.getName() + " 에러: " + e.getMessage());
                    }
                }
            }
        }

        // 💾 파일 저장 배관 가동!
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./result_tm.txt"))) {
            for (String phone : finalTmList) {
                writer.write(phone);
                writer.newLine(); // 한 줄 띄우기
            }
            System.out.println("\n🎉 TM용 데이터 무결성 정제 완료!");
            System.out.println("📍 저장 위치: C:/hangyudae/result_tm.txt");
            System.out.println("📊 총 확보된 유효 번호: " + finalTmList.size() + "개");
        } catch (Exception e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}