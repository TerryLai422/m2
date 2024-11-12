package com.thinkbox.m2.m2_questdb.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class QuestDBService {
    @PostConstruct
    public void init() {
        System.out.println("BEFORE TEST");
//        main();
        System.out.println("AFTER TEST");
    }

    public void test() {
        String csvFilePath = "/Users/admin/aapl.us.txt";
//		try {
//			byte[] fileBytes = Files.readAllBytes(Paths.get(csvFilePath));
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
        String questDBUrl = "http://localhost:9000/imp";
        String tableName = "your_table_name";

        try {
            importCsvToQuestDB(csvFilePath, questDBUrl, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hello() {
        RestTemplate restTemplate = new RestTemplate();

        // Perform a POST request
        String postUrl = "http://localhost:9000/imp?fmt=json&";
        String requestJson = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> postResponse = restTemplate.exchange(postUrl, HttpMethod.POST, requestEntity, String.class);
        System.out.println("POST Response: " + postResponse.getBody());
    }


    public void main() {
        String csvFilePath = "/Users/admin/aapl.us.txt";
        String questDBUrl = "http://localhost:9000/imp?fmt=json&name=aapl.us.txt&partitionBy=NONE&forceHeader=false&overwrite=false&skipLev=false&delimiter=&atomicity=skipCol&maxUncommitedRows=500000";
        String tableName = "your_table_name";

        try {
            importCsvToQuestDB(csvFilePath, questDBUrl, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void importCsvToQuestDB(String csvFilePath, String questDBUrl, String tableName) throws IOException {
        // Read the CSV file
        String csvContent = new String(Files.readAllBytes(Paths.get(csvFilePath)));

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("table", tableName);

        // Create the HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(csvContent, headers);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Send the POST request
        ResponseEntity<String> response = restTemplate.postForEntity(questDBUrl, entity, String.class);

        // Check the response
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("CSV file imported successfully to QuestDB table: " + tableName);
			System.out.println(response.getBody());
        } else {
            System.out.println("Error: HTTP response code " + response.getStatusCode());
        }
    }
}