package com.thinkbox.m2.m2_questdb.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@RestController
@RequestMapping("/test")
@Slf4j
public class Controller {
    @GetMapping(value = "/get")
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping(value = "/post")
    public ResponseEntity<Object> post(@RequestBody String request) {
        System.out.println("Body:" + request);
        return ResponseEntity.ok().body("ok");
    }


    public void main(String body) {
        String url = "http://127.0.0.1:9000/imp?name=table_name";
        String filePath = "/Users/admin/a1.txt";

        RestTemplate restTemplate = new RestTemplate();

        // Create HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create HttpEntity
        FileSystemResource fileResource = new FileSystemResource(new File(filePath));
        HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(fileResource, headers);

        // Post request
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Print response
        System.out.println(response.getBody());
    }
}
