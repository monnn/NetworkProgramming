package com.moni;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class ImageDownloader {

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        OutputStream out = new FileOutputStream(destinationFile);
        byte[] b = new byte[2048];
        int length;
        while ((length = in.read(b)) != -1) {
            out.write(b, 0, length);
        }
        in.close();
        out.close();
    }

    public static void main(String[] args) throws Exception {
        String imageUrl = "http://upbook.le.tsdoit.org/upbook2012_milen.petrov.png";
        String destinationFile = "image.jpg";
        saveImage(imageUrl, destinationFile);
    }
}
