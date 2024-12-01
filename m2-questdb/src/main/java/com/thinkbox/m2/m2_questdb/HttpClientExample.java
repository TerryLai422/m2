package com.thinkbox.m2.m2_questdb;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.closeableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.io.IOException;
import java.io.File;

public class HttpClientExample {
    public static void main(String[] args) {
        Path startPath = Paths.get("/Users/admin/historial_data");
        String urlTemplate = "http://%s/imp?fmt=json&forceHeader=true&name=%s";
        String hostName = "127.0.0.1:9000";
        String tableName = "historial_raw_d";

        String url = String.format(urlTemplate, hostName, tableName);

        try {
            List<String> fileNames = listFiles(startPath);
            for (String fullFileName : fileNames) {
                importFile(url, fullFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void importFile(String url, String fileName) {
        System.out.println(fileName);
        File file = new File(fileName);
        try (closeableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("data", file);

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);

            HttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println("Response: " + responseString);
            }
        } catch (Exception e) {
            System.out.println("ERROR:" + fileName);
           // e.printStackTrace();
            copyToErrorDirectory(file);
        }
    }

    public static void copyToErrorDirectory(File file) {
        try {
            String parentPath = file.getParent();
            String writePath = parentPath.replace("/Users/admin/historial_data/",
                    "/Users/admin/historial_error/");
            Path writeDir = Paths.get(writePath);
            if (!Files.exists(writeDir)) {
                Files.createDirectories(writeDir);
            }
            Path targetPath = writeDir.resolve(file.getName());
            Files.copy(file.toPath(), targetPath);
            System.out.println("File copied to /error/ directory: " + targetPath.toString());
        } catch (IOException ioException) {
            System.err.println("Failed to copy file to /error/ directory: " + ioException.getMessage());
            ioException.printStackTrace();
        }
    }
    public static List<String> listFiles(Path startPath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Files.walkFileTree(startPath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.getFileName().startsWith(".DS_Store")) {
                    fileNames.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.err.println("Failed to visit file: " + file.toString() + " (" + exc.getMessage() + ")");
                return FileVisitResult.CONTINUE;
            }
        });
        return fileNames;
    }
}