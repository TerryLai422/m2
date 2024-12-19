package com.thinkbox.m2.m2_questdb.controller;

import com.thinkbox.m2.m2_questdb.Constants;
import com.thinkbox.m2.m2_questdb.ImportRawData;
import com.thinkbox.m2.m2_questdb.InsertIntoHistorial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class Controller implements Constants {
    @Value("${path.importHistoricalFile}")
    String importHistoricalFilePath;

    @Value("${path.importHistoricalError}")
    String importHistoricalErrorPath;

    @Value("${path.importDailyFile}")
    String importDailyFilePath;

    @Value("${path.importDailyError}")
    String importDailyErrorPath;
    @PostMapping(value = "/post")
    public ResponseEntity<Object> post(@RequestBody Map<String, String> request) {
        System.out.println("request:" + request);
        String action = request.getOrDefault("action", "");
        String returnValue = "cannot find action";
        if ("import_raw_file".equals(action)) {
            returnValue = Long.valueOf(ImportRawData.run(importHistoricalFilePath, importHistoricalErrorPath,
                    importUrlTemplate, hostName, "historical_raw_d")).toString();
        } else if ("insert_into_historical".equals(action)) {
            returnValue = Long.valueOf(InsertIntoHistorial.run()).toString();
        }
        return ResponseEntity.ok().body(returnValue);
    }
}

