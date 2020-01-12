package com.netanel.study.java.future;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    //public static final String  url = "api.openweathermap.org/data/2.5/weather?q=London,uk";
    public static final String  url = "http://www.google.com";

    public static void main(String argv[]) throws IOException, ExecutionException, InterruptedException {
        System.out.println("======================== Sync http request...========================");
        System.out.println("Sync http request...");
        String result1 = okHttpSyncTest();
        System.out.println("GET response is " + result1);
        System.out.println("======================== aSync http request with future...========================");
        String result2 = okHttpASyncTest();
        System.out.println("GET response is " + result2);
    }

    public static String okHttpSyncTest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        System.out.println("responseString is " + responseString);
        return responseString;
    }

    public static String okHttpASyncTest() throws IOException, ExecutionException, InterruptedException {
        Future<Response>  future = makeRequest();
        Response response = future.get();
        String responseString = response.body().string();
        System.out.println("responseString is " + responseString);
        return responseString;
    }


    private static Future<Response> makeRequest(){
        OkHttpResponseFuture result = new OkHttpResponseFuture();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(result);
        return result.future;
    }
}
