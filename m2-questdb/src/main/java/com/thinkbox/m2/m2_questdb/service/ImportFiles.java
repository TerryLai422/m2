package com.thinkbox.m2.m2_questdb.service;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class ImportFiles implements Constants {
    public static Object singlethread(String url, Path startPath, String errorPath) {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        int count = 0;
        try {
            List<String> fileNames = listFiles(startPath);
            for (String fullFileName : fileNames) {
                if (importFile(url, fullFileName, startPath.toAbsolutePath().toString(), errorPath)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("duration: " + (end - start));
        map.put("duration", end - start);
        map.put("count", count);
        return map;
    }

/*
    public static void multithread(String url, Path startPath, String importHistoricalFilePath, String errorPath) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            List<String> fileNames = listFiles(startPath);
            for (String fullFileName : fileNames) {
                executorService.submit(() -> importFile(url, fullFileName, importHistoricalFilePath, errorPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) { // Wait for all tasks to complete
        }
        long end = System.currentTimeMillis();
        System.out.println("duration: " + (end - start));
    }
*/

    public static boolean importFile(String url, String fileName, String importHistoricalFilePath, String errorPath) {
        System.out.println(fileName);
        File file = new File(fileName);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
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
            return true;
        } catch (Exception e) {
            System.out.println("ERROR:" + fileName);
            copyToErrorDirectory(file, importHistoricalFilePath, errorPath);
        }
        return false;
    }

    public static void copyToErrorDirectory(File file, String importHistoricalFilePath, String errorPath) {
        try {
            String parentPath = file.getParent();
            String writePath = parentPath.replace(importHistoricalFilePath, errorPath);
            Path writeDir = Paths.get(writePath);
            if (!Files.exists(writeDir)) {
                Files.createDirectories(writeDir);
            }
            Path targetPath = writeDir.resolve(file.getName());
            Files.copy(file.toPath(), targetPath);
            System.out.println("File copied to /error/ directory: " + targetPath);
        } catch (IOException ioException) {
            System.err.println("Failed to copy file to /error/ directory: " + ioException.getMessage());
            ioException.printStackTrace();
        }
    }

    public static List<String> listFiles(Path startPath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Files.walkFileTree(startPath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!file.getFileName().startsWith(".DS_Store")) {
                    fileNames.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Failed to visit file: " + file.toString() + " (" + exc.getMessage() + ")");
                return FileVisitResult.CONTINUE;
            }
        });
        return fileNames;
    }
}