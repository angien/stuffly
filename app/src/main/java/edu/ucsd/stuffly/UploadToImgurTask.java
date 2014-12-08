package edu.ucsd.stuffly;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Angie on 12/8/14.
 */
public class UploadToImgurTask extends AsyncTask<File, Void, JSONObject> {
    String upload_to;

    @Override
    protected JSONObject doInBackground(File... params) {
        final String upload_to = "https://api.imgur.com/3/upload.json";
        final String API_key = "API_KEY";
        final String TAG = "Awais";

        String client_id = "6e2e0e1149a3b44";

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(upload_to);


        try {
            final MultipartEntity entity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);

            entity.addPart("image", new FileBody(params[0]));
            entity.addPart("key", new StringBody(API_key));

            httpPost.setHeader("Authorization", "Client-ID " + client_id);
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost,
                    localContext);

            String response_string = EntityUtils.toString(response
                    .getEntity());

            JSONObject json = new JSONObject(response_string);

            Log.e("TO IMGUR!!!!!", json.toString()); //for my own understanding

            return json;
        } catch (Exception e) {
            Log.e("WHATATATAT", "why");
            e.printStackTrace();
        }





        return null;
    }
}