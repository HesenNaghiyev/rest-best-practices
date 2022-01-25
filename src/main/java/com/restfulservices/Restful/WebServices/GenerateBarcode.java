package com.restfulservices.Restful.WebServices;

import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class GenerateBarcode {

    public static void main(String[] args) {

        try {

            String data = "https://hasannaghiyev.com";
            String path = "/Users/hesennaghiyev/Desktop/hasannaghiyevbarcode.jpg";

            Code128Writer writer = new Code128Writer();
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128, 500, 300);

            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

            System.out.println("Barcode created...");

        } catch(Exception e) {
            System.out.println("Error while creating barcode");
        }
    }

}
