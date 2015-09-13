package com.example.citytour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream inputStream = null;
	static JSONObject jobj = null;
	static String json = "";
	public JSONParser(){

	}
	public JSONObject makeHttpRequest(String url){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			HttpResponse httpresponse = httpclient.execute(httppost);
			if(httpresponse!=null){
				HttpEntity httpentity = httpresponse.getEntity();
				inputStream = httpentity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if(inputStream !=null){
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while((line = reader.readLine())!=null){
						sb.append(line+"\n");	
						}
					inputStream.close();
					json = sb.toString();
					try {
						jobj = new JSONObject(json);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobj;
	}

	public String getJSONFromUrl(String url) {
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            json = sb.toString();
            inputStream.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return json;

    }
}