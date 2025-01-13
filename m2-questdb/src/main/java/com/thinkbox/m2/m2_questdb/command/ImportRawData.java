package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ImportFiles;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportRawData implements Constants {
    public static Object run(String url, String importHistoricalFilePath, String importHistoricalErrorPath) {
        Path startPath = Paths.get(importHistoricalFilePath);
        return ImportFiles.singlethread(url, startPath, importHistoricalErrorPath);
    }
}