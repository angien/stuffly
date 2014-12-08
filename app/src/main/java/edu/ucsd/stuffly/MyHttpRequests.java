package edu.ucsd.stuffly;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MadnessX on 12/2/2014.
 */
public class MyHttpRequests extends AsyncTask<String, Integer, String> {
    protected String doInBackground(String ... params) {
//        int count = urls.length;
//        long totalSize = 0;
//        for (int i = 0; i < count; i++) {
//            totalSize += Downloader.downloadFile(urls[i]);
//            publishProgress((int) ((i / (float) count) * 100));
//            // Escape early if cancel() is called
//            if (isCancelled()) break;
//        }
//        return totalSize;
        String serverURL = "http://stuffly.herokuapp.com" + params[0];

        if (params[1].equals("GET")) { // A GET
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(serverURL);
            try{
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if(statusCode == 200){
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failedet JSON object");
                }
            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return builder.toString();
        }
        else if(params[1].equals("POST")){ // A POST
         // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(serverURL);

            try {
                // Add your data
                JSONObject obj = new JSONObject(params[2]);
                httppost.setEntity(new StringEntity(obj.toString()));
                httppost.setHeader("Content-type","application/json");
                Log.i("httppost", httppost.toString());

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                int temp = response.getStatusLine().getStatusCode();
                Log.i("MyHttpRequests POST", ""+temp);
                if (temp == 200) return "true";
            } catch (Exception e){
                Log.e("MyHttpRequests POST", e.toString());
            }
        }
        else{//PUT
            // Create a new HttpClient and Post Header

            HttpClient httpclient = new DefaultHttpClient();
            StringBuilder builder = new StringBuilder();
            HttpPut httpPut = new HttpPut(serverURL);
            try{
                JSONObject obj = new JSONObject(params[2]);
                httpPut.setEntity(new StringEntity(obj.toString()));
                httpPut.setHeader("Content-type","application/json");
                Log.i("httpput", httpPut.toString());

                HttpResponse response = httpclient.execute(httpPut);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if(statusCode == 200){
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                } else {
                    Log.e(MainActivity.class.toString(), "Failed to PUT JSON object");
                }
            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return builder.toString();





        }
        return "false";


    }

//    protected void onProgressUpdate(Integer... progress) {
//        setProgressPercent(progress[0]);
//    }
}
