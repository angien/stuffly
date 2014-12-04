package edu.ucsd.stuffly;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by ryanliao on 12/2/14.
 */
public class MyHttpRequests
{
    private HttpClient client = new DefaultHttpClient();
    private static final String base_url = "http://test-master-env-ecmnn89sfm.elasticbeanstalk.com/api";
    private static final String login_url = base_url + "/user/login";

    public MyHttpRequests(){
    }
    public boolean login(String email, String password)
    {
        HttpPost post_request = new HttpPost(login_url);
        post_request.addHeader("Content-type", "application/json");
        String json_body = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";
        InputStream in = null;
        char[] buf = new char[500];
        try{
            Log.e("http", json_body);

            StringEntity se = new StringEntity(json_body);
            post_request.setEntity(se);
            HttpResponse response = client.execute(post_request);
            HttpEntity getRespEn = response.getEntity();
            in = getRespEn.getContent();

            Reader reader = new InputStreamReader(in);
            reader.read(buf);

            String resp = new String(buf);
            JSONObject serverResponse = new JSONObject(resp);
            String resp_email = serverResponse.getString("email");
            String resp_pass = serverResponse.getString("password");
            String resp_id = serverResponse.getString("_id");
            new UserID(resp_id);
            if(resp_email.equals(email) && resp_pass.equals(password)){
                return true;
            }
            return false;


        }catch(IOException e){
            Log.e("http", "IOException from login post");
            return false;

        }catch (JSONException e) {
            Log.e("http", "JSONException from login");
            return false;
        }
    }


}
