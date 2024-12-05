package com.thinkbox.m2.m2_questdb;

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
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImportRawData {
    public static void main(String[] args) {
        Path startPath = Paths.get("/Users/admin/historial_data");
        String urlTemplate = "http://%s/imp?fmt=json&forceHeader=true&name=%s";
        String hostName = "127.0.0.1:9000";
        String tableName = "historial_raw_d";
        String url = String.format(urlTemplate, hostName, tableName);
        ImportFiles.singlethread(url, startPath);
    }
}