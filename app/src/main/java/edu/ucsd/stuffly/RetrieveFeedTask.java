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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
public class RetrieveFeedTask extends AsyncTask<String, Integer, String> {
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

        if (params[1].equals("GET")) {
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
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
        else {
            // Create a new HttpClient and Post Header
            Log.i("fuck", "shit");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            try {
                // Add your data


                JSONObject obj = new JSONObject("{'password':'HAHa','email':'HAHa','lastname':'HA','firstname':'HAR'}");
                httppost.setEntity(new StringEntity(obj.toString()));
                httppost.setHeader("Content-type","application/json");
                Log.i("httppost", httppost.toString());

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                String temp = EntityUtils.toString(response.getEntity());
                Log.i("RetrieveFeedTask POST", temp);
                return "true";
            } catch (Exception e){
                Log.e("RetrieveFeedTask POST", e.toString());
            }
            return "false";
        }



    }

//    protected void onProgressUpdate(Integer... progress) {
//        setProgressPercent(progress[0]);
//    }
}
