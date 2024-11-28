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
//            String url = "http://192.168.51.230:9000/imp?fmt=json&name=historial_raw_d&partitionBy=NONE&forceHeader=false&overwrite=false&skipLev=false&delimiter=&atomicity=skipCol&maxUncommitedRows=500000";
//            String url = "http://127.0.0.1:9000/imp?fmt=json&name=historial_raw_d&partitionBy=NONE&forceHeader=false&overwrite=false&skipLev=false&delimiter=&atomicity=skipCol&maxUncommitedRows=500000";
                        String url = "http://127.0.0.1:9000/imp?fmt=json";
            String filePath = "/Users/admin/aapl.us.txt";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.ORIGIN, "http://192.168.51.230:9000");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
//            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSystemResource.contentLength()));
            System.out.println(fileSystemResource.contentLength());
            System.out.println("headers:" + headers);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            String schema = "[" +
//                    "{\"name\":\"symbol\", \"type\": \"varchar\"}," +
//                    "{\"name\":\"side\", \"type\": \"varchar\"}," +
//                    "{\"name\":\"price\", \"type\": \"double\"}," +
//                    "{\"name\":\"amount\", \"type\": \"double\"}," +
//                    "{\"name\":\"timestamp\", \"type\": \"timestamp\", \"pattern\": \"yyyy-MM-ddTHH:mm:ss.SSSUUUZ\"}" +
//                    "]";
//            body.add("schema", schema);
            body.add("data", new FileSystemResource(new File(filePath)));
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Headers:" + response.getHeaders());
            System.out.println("Response Body: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}