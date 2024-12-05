package com.thinkbox.m2.m2_questdb;

import java.nio.file.Path;
import java.nio.file.Paths;

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