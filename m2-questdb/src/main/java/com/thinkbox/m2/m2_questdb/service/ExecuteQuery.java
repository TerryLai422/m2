package com.thinkbox.m2.m2_questdb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ExecuteQuery {
    public static Object run(String url, String query) {
        String count = "true";
        Map<String, Object> map = new HashMap<>();
        long start = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            System.out.println("query:" + query);
            URI uri = new URIBuilder(url)
                    .addParameter("query", query)
                    .addParameter("count", count)
                    .build();

            HttpGet request = new HttpGet(uri);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseMap = mapper.readValue(responseString, Map.class);
                map.put("response", responseMap);
                System.out.println("Response: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("duration: " + (end - start));
        map.put("duration", end - start);
        return map;
    }
}