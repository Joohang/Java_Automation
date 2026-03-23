package com.master.auto.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class OcrEngine {
    private Tesseract tesseract;

    public OcrEngine() {
        tesseract = new Tesseract();
        tesseract.setDatapath("./tessdata");
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

    public String scanPdf(String pdfPath) throws IOException {
        try(PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            return "PDF 읽기 중 오류 발생: " + e.getMessage();
        }
    }

    public String scanAny(File file) throws IOException {
        String fileName = file.getName().toLowerCase();
        String path = file.getAbsolutePath();

        if (fileName.endsWith(".pdf")) {
            String content = scanPdf(path);
            if (content == null || content.trim().length() < 5) {
                System.out.println("텍스트 추출 실패 ");
                return  scanImage(path);
            }
            return content;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".png")||fileName.endsWith("jpeg")) {
            return scanImage(file.getAbsolutePath());
        }
        return "";
    }

}

