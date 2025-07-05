package com.thinkbox.m2.m2_questdb.service;

import com.thinkbox.m2.m2_questdb.command.*;
import com.thinkbox.m2.m2_questdb.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    public Object insertIntoHistorical_5m(Map<String, Object> request) {
        String type = request.getOrDefault("type", "d").toString();
        String date = request.getOrDefault("date", "").toString();
        String url = String.format(execUrlTemplate, hostName);
        return InsertIntoHistorial_5m.run(url, type, date);
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

    public Object selectFromHistoricalForTicker(Map<String, Object> request) {
        String ticker = request.getOrDefault("ticker", "").toString();
        String url = String.format(execUrlTemplate, hostName);
        Map<String, Object> map = (Map<String, Object>) SelectFromHistorialForTicker.run(url, ticker);
        Map<String, Object> response = (Map<String, Object>) map.get("response");
        List<List<Object>> dataset = (List<List<Object>>) response.get("dataset");
        System.out.println("dataset:" + dataset.get(0));
        List<Map<String, Object>> list = new ArrayList<>();
        for (List<Object> data : dataset) {

            Map<String, Object> i = new HashMap<>();
            i.put("date", data.get(0).toString().substring(0, 10));
            i.put("open", data.get(1));
            i.put("high", data.get(2));
            i.put("low", data.get(3));
            i.put("close", data.get(4));
            i.put("volume", BigDecimal.valueOf((Double) data.get(5)).setScale(0, RoundingMode.HALF_UP));
            list.add(i);
        }
        return list;
    }

    public Object calculateIndicator(Map<String, Object> request) {
        String type = request.getOrDefault("type", "").toString();
        String url = String.format(execUrlTemplate, hostName);
        boolean etf = Boolean.parseBoolean(request.getOrDefault("etf", Boolean.FALSE).toString());
        if ("52w".equals(type)) {
            String sourceTable = etf ? "historical_etf_d" : "historical_d";
            String targetTable = etf ? "indicator_etf_52w" : "indicator_52w";
            return InsertIntoIndicator_52w.run(url, sourceTable, targetTable);
        }
        int day = Integer.parseInt(request.getOrDefault("day", String.valueOf(0)).toString());
        if (day > 0) {
            String sourceTable = etf ? "historical_etf_d" : "historical_d";
            if ("AV".equals(type)) {
                String targetTable = etf ? "indicator_etf_AV" : "indicator_AV";
                return InsertIntoIndicator.run(url, "vol", "AV", day, sourceTable, targetTable);
            } else if ("MA".equals(type)) {
                String targetTable = etf ? "indicator_etf_MA" : "indicator_MA";
                return InsertIntoIndicator.run(url, "close", "MA", day, sourceTable, targetTable);
            }
        }
        return Map.of("error", "invalid parameter");
    }
}