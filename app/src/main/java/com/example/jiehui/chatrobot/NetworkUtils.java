package com.example.jiehui.chatrobot;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by jiehui on 6/6/17.
 */

public class NetworkUtils {
    private static final String Base_Url = "http://www.tuling123.com/openapi/api";

    private static final String ParamOne = "key";

    private static final String ParamTwo = "info";




    public static URL buildUrl(String tulingSearchQuery){
        Uri builtUri = Uri.parse(Base_Url).buildUpon()
                .appendQueryParameter(ParamOne,"62e81e7de14044dca3b10799cb0cce55")
                .appendQueryParameter(ParamTwo,tulingSearchQuery)
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try{
            httpURLConnection.setRequestMethod("POST");
            InputStream in = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();

            }else {
                return null;
            }

        } finally {
            httpURLConnection.disconnect();
        }

    }


}
