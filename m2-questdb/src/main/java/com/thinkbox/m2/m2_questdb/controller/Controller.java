package com.thinkbox.m2.m2_questdb.controller;

import com.thinkbox.m2.m2_questdb.command.CleanUpData;
import com.thinkbox.m2.m2_questdb.command.ImportRawData;
import com.thinkbox.m2.m2_questdb.command.InsertIntoHistorial;
import com.thinkbox.m2.m2_questdb.command.InsertIntoIndicator;
import com.thinkbox.m2.m2_questdb.constants.Constants;
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
        } else if ("clean_up_historical".equals(action)) {
            returnValue = Long.valueOf(CleanUpData.run()).toString();
        } else if ("indicator".equals(action)) {
            int day = Integer.parseInt(request.getOrDefault("day", String.valueOf(0)));
            if (day > 0) {
                String type = request.getOrDefault("type", "");
                if ("AV".equals(type)) {
                    returnValue = Long.valueOf(InsertIntoIndicator.run("vol", "AV", day, "indicators_AV")).toString();
                } else if ("MA".equals(type)) {
                    returnValue = Long.valueOf(InsertIntoIndicator.run("close", "MV", day, "indicators_MA")).toString();
                }
            }
        }
        return ResponseEntity.ok().body(returnValue);
    }
}