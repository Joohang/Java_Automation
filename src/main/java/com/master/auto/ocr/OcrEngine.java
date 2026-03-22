package com.master.auto.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OcrEngine {
    private Tesseract tesseract;

    public OcrEngine() {
        tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        tesseract.setLanguage("kor+eng");
    }

    public String scanImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            return tesseract.doOCR(imageFile);
        } catch (Exception e) {
            return "스캔 중 오류 발생 : " + e.getMessage();
        }
    }
}
