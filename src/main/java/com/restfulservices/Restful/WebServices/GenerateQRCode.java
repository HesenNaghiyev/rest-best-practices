package com.restfulservices.Restful.WebServices;

import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class GenerateQRCode {
    public static void main(String[] args) throws Exception {

        String data = "https://github.com/HesenNaghiyev";
        String path = "/Users/hesennaghiyev/Desktop/github.jpg";

        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, 500, 500);

        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));


    }
}
