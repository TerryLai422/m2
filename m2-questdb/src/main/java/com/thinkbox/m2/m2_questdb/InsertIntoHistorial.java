package com.thinkbox.m2.m2_questdb;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class InsertIntoHistorial {
    public static void main(String[] args) {
        String url = "http://localhost:9000/exec";
        String query = "INSERT INTO historial_d\n" +
                "SELECT \n" +
                "    replace(ticker, '.US', ''), \n" +
                "    to_timestamp(date, 'yyyyMMdd') AS date, \n" +
                "    open, \n" +
                "    high, \n" +
                "    low, \n" +
                "    close, \n" +
                "    vol\n" +
                "FROM historial_raw_d\n" +
                "WHERE date >= '19700101' \n" +
                "ORDER BY date ASC;";

        ExecuteQuery.execute(url, query);;
    }
}