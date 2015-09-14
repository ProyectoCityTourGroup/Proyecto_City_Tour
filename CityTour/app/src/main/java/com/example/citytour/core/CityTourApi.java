package com.example.citytour.core;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by alvaro on 14/09/15.
 */
public class CityTourApi {

    private final static int GET = 1;
    public final static int POST = 2;
    private static final String CHARSET = "UTF-8";

    public static String getResponse(String url, int method, List<NameValuePair> params, Map<String, String> headers) {
        String response = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity;
            HttpResponse httpResponse = null;

            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);

                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                if (headers != null) {
                    for (String key : headers.keySet()) {
                        httpPost.setHeader(key, headers.get(key));
                    }
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, CHARSET);
                    url += "?" + paramString;
                }

                HttpGet httpGet = new HttpGet(url);
                if (headers != null) {
                    for (String key : headers.keySet()) {
                        httpGet.setHeader(key, headers.get(key));
                    }
                }

                httpResponse = httpClient.execute(httpGet);

            }

            assert httpResponse != null;
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    public static String getResponse(String url) {
        return CityTourApi.getResponse(url, CityTourApi.GET, null, null);
    }

    public static String googleMapsRouteURL(double originLat, double originLng, double destinationLat, double destinationLng) {
        String urlString = "http://maps.googleapis.com/maps/api/directions/json";
        urlString += "?origin=" + Double.toString(originLat) + "," + Double.toString(originLng);
        urlString += "&destination=" + Double.toString(destinationLat) + "," + Double.toString(destinationLng);
        urlString += "&sensor=false&mode=walking&alternatives=true";//mode=transit para transporte publico
        return urlString;
    }
}
