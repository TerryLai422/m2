package com.thinkbox.m2.m2_questdb.controller;

import com.thinkbox.m2.m2_questdb.service.M2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class M2Controller {
    @Autowired
    M2Service m2Service;

    @PostMapping(value = "/post")
    public ResponseEntity<Object> post(@RequestBody Map<String, Object> request) {
        System.out.println("request:" + request);
        String action = request.getOrDefault("action", "").toString();
        if ("import_raw_file".equals(action)) {
            return ResponseEntity.ok().body(m2Service.importRaw(request));
        } else if ("select_from_historical_for_ticker".equals(action)) {
            return ResponseEntity.ok().body(m2Service.selectFromHistoricalForTicker(request));
        } else if ("select_from_historical".equals(action)) {
            return ResponseEntity.ok().body(m2Service.selectFromHistorical(request));
        } else if ("insert_into_historical".equals(action)) {
            String type = request.getOrDefault("type", "").toString();
            if ("etf_5m".equals(type) || "5m".equals(type)) {
                return ResponseEntity.ok().body(m2Service.insertIntoHistorical_5m(request));
            }
            return ResponseEntity.ok().body(m2Service.insertIntoHistorical(request));
        } else if ("clean_up_historical_raw".equals(action)) {
            return ResponseEntity.ok().body(m2Service.cleanUp(request, "historical_raw_"));
        } else if ("clean_up_indicator".equals(action)) {
            return ResponseEntity.ok().body(m2Service.cleanUp(request, "indicator_"));
        } else if ("indicator".equals(action)) {
            return ResponseEntity.ok().body(m2Service.calculateIndicator(request));
        }
        return ResponseEntity.ok().body(Map.of("error", "cannot find action"));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> get(@RequestParam String ticker) {
        Map<String, Object> request = new HashMap<>();
        request.put("ticker", ticker);
        return ResponseEntity.ok().body(m2Service.selectFromHistoricalForTicker(request));
    }
}