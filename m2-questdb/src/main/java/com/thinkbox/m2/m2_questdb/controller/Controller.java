package com.thinkbox.m2.m2_questdb.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class Controller {

    @PostMapping(value = "/post")
    public ResponseEntity<Object> post(@RequestBody String request) {
        main1();
        return ResponseEntity.ok().body("ok");
    }

    private static final String UPLOAD_DIR = "/Users/admin/upload/";

    @PostMapping(value = "/imp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> handleImp(@RequestPart("data") MultipartFile file,
                                                         @RequestHeader Map<String, String> headers) {
        try {
            System.out.println("Headers:" + headers);
            // Save the file locally
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
//            Files.createDirectories(path.getParent()); // Ensure the directory exists
            Files.write(path, file.getBytes());

            // Process additional form data if needed
            // e.g., log the name parameter
            System.out.println("data: " + file.getOriginalFilename());
//            System.out.println("schema: " + schema );

            Map<String, String> map = new HashMap<>();
            map.put("status", "OK");

            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("status", "Failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    public void main1() {
        try {
            String url = "http://127.0.0.1:8080/test/imp";
//            String url = "http://127.0.0.1:9000/imp?fmt=json";
            String filePath = "/Users/admin/b1.txt";

            RestTemplate restTemplate = new RestTemplate();

            // Create HttpHeaders
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

//            String fileContent = Files.readString(Path.of(filePath));
//            curl -F schema='[{"name":"timestamp", "type": "TIMESTAMP", "pattern": "yyyy-MM-dd HH:mm:ss"}]' -F data=@energy_2018.csv 'http://localhost:9000/imp?overwrite=false&name=energy_2018&timestamp=timestamp&partitionBy=MONTH'
            // Create a MultiValueMap to hold the file and form fields
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

//            body.add("data", fileContent);
            body.add("data", new FileSystemResource(new File(filePath)));

            // Create HttpEntity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send POST request
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            // Print response
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }
}

