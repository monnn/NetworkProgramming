package com.moni;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class FunctionClass {


    private static final int BUFFER_SIZE = 4096;

    public static void zip(String fileName, String zipFile) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        ZipEntry zipEntry = new ZipEntry(fileName);
        out.putNextEntry(zipEntry);
        FileInputStream in = new FileInputStream(fileName);
        byte[] buf = new byte[1024];
        int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
    }

    public static void zipFew(String zipFile, String ... fileName) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        FileInputStream in = null;
        for (String name : fileName) {
            ZipEntry zipEntry = new ZipEntry(name);
            out.putNextEntry(zipEntry);
            in = new FileInputStream(name);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
        in.close();
        out.close();
    }

    public static void unzip(String zipFile, String destDirectory) throws IOException {
        File destination = new File(destDirectory);
        if (!destination.exists()) {
            destination.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] bytesIn = new byte[BUFFER_SIZE];
                int read;
                while ((read = zipIn.read(bytesIn)) != -1) {
                    bos.write(bytesIn, 0, read);
                }
                bos.close();
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    public static void union(String file, String newFileName) throws IOException {
        FileReader reader;
        File file1 = new File(file);
        reader = new FileReader(file1);
        BufferedReader in = new BufferedReader(reader);
        File newFile = new File(newFileName);
        FileWriter writer = new FileWriter(newFile, true);
        BufferedWriter out = new BufferedWriter(writer);
        String line;
        while ((line = in.readLine()) != null) {
            out.write(line);
            out.newLine();
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "file1.txt";
        String fileName2 = "file2.txt";
        String fileName3 = "file3.txt";
        String zipFile = "archive.zip";
        String folder = "NewFolder";
        zipFew(zipFile, fileName, fileName2, fileName3);
        unzip(zipFile, folder);
        union(fileName, "newFile.txt");
        union(fileName2, "newFile.txt");
        union(fileName3, "newFile.txt");
        zipFew("newArchive.zip", fileName, fileName2, fileName3, "newFile.txt");
    }
}
