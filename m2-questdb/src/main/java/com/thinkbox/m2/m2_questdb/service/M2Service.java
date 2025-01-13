package com.thinkbox.m2.m2_questdb.service;

import com.thinkbox.m2.m2_questdb.command.*;
import com.thinkbox.m2.m2_questdb.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class M2Service implements Constants {
    @Value("${m2.path.importHistoricalFile}")
    String importHistoricalFilePath;

    @Value("${m2.path.importHistoricalError}")
    String importHistoricalErrorPath;

    @Value("${m2.hostName}")
    String hostName;

    public Object importRaw(Map<String, Object> request) {
        String type = request.getOrDefault("type", "d").toString();
        String url = String.format(importUrlTemplate, hostName, "historical_raw_" + type);
        return ImportRawData.run(url, importHistoricalFilePath, importHistoricalErrorPath);
    }

    public Object cleanUp(Map<String, Object> request, String tablePrefix) {
        String url = String.format(execUrlTemplate, hostName);
        Map<String, Object> map = new HashMap<>();
        if (request.containsKey("list")) {
            Object listObject = request.get("list");
            List<String> list = (List<String>) listObject;
            for (String type : list) {
                Object object = CleanUpData.run(url, tablePrefix, type);
                map.put(type, object);
            }
        } else {
            String type = request.getOrDefault("type", "d").toString();
            Object object = CleanUpData.run(url, tablePrefix, type);
            map.put(type, object);
        }
        return map;
    }

    public Object insertIntoHistorical(Map<String, Object> request) {
        String type = request.getOrDefault("type", "d").toString();
        String date = request.getOrDefault("date", "").toString();
        String url = String.format(execUrlTemplate, hostName);
        return InsertIntoHistorial.run(url, type, date);
    }

    public Object selectFromHistorical(Map<String, Object> request) {
        String type = request.getOrDefault("type", "d").toString();
        String url = String.format(execUrlTemplate, hostName);
        return SelectFromHistorial.run(url, type);
    }

    public Object calculateIndicator(Map<String, Object> request) {
        String type = request.getOrDefault("type", "").toString();
        String url = String.format(execUrlTemplate, hostName);
        if ("52w".equals(type)) {
            return InsertIntoIndicator_52w.run(url);
        }
        int day = Integer.parseInt(request.getOrDefault("day", String.valueOf(0)).toString());
        if (day > 0) {
            if ("AV".equals(type)) {
                return InsertIntoIndicator.run(url, "vol", "AV", day, "indicator_AV");
            } else if ("MA".equals(type)) {
                return InsertIntoIndicator.run(url, "close", "MA", day, "indicator_MA");
            }
        }
        return Map.of("error", "invalid parameter");
    }
}