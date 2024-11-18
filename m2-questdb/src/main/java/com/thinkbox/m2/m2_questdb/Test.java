package com.thinkbox.m2.m2_questdb;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

public class Test {
    public static void main(String[] arg) {
        try {
            String url = "http://127.0.0.1:9000/imp?fmt=json&overwrite=true&forceHeader=true";
            String filePath = "/Users/admin/btc_trades.csv";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            String schema = "[" +
                    "{\"name\":\"symbol\", \"type\": \"varchar\"}," +
                    "{\"name\":\"side\", \"type\": \"varchar\"}," +
                    "{\"name\":\"price\", \"type\": \"double\"}," +
                    "{\"name\":\"amount\", \"type\": \"double\"}," +
                    "{\"name\":\"timestamp\", \"type\": \"timestamp\"}" +
                    "]";
            body.add("schema", schema);
            body.add("data", new FileSystemResource(new File(filePath)));
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Headers:" + response.getHeaders());
            System.out.println("Response Body: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}