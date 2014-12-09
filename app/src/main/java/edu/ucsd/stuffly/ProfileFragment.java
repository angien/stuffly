package edu.ucsd.stuffly;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ryanliao on 10/31/14.
 */
public class ProfileFragment extends Fragment
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    File photoFile;
    String mCurrentPhotoPath;
    String image_url;

    ImageView profileImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView firstnameField = (TextView) rootView.findViewById(R.id.profile_first_name);
        final TextView lastnameField = (TextView) rootView.findViewById(R.id.profile_last_name);
        final TextView emailField = (TextView) rootView.findViewById(R.id.profile_email);
        final TextView passwordField = (TextView) rootView.findViewById(R.id.profile_password);
        MyHttpRequests rtaskget = new MyHttpRequests();
        String id = UserID.id;

        ImageLoader il = ImageLoader.getInstance();

        image_url = "default";
        try{
            //rtaskget.execute("/api/user/" + id,"GET");
            JSONObject userInfo = new JSONObject(rtaskget.execute("/api/user/" + id,"GET").get());
            Log.e("The returned string for profiel", userInfo.toString());

            email = userInfo.getString("email");
            password = userInfo.getString("password");
            firstName = userInfo.getString("firstname");
            lastName = userInfo.getString("lastname");
            //Log.i("STUFFFFFF url", "wat" + feed.get(i));

        }
        catch (JSONException e) {
            firstName = "";
            lastName = "";
        }
        catch(Exception e){
            Log.e("FeedFragment GET", e.toString());
        }
        firstnameField.setText(firstName);
        lastnameField.setText(lastName);
        emailField.setText(email);
        passwordField.setText(password);

        //on-campus or off-campus
        Spinner spinner = (Spinner) rootView.findViewById(R.id.profile_location_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.locations_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        profileImg = (ImageView) rootView.findViewById(R.id.profile_pic);
        final Button profileImgEdit = (Button) rootView.findViewById(R.id.edit_profile_img_btn);
        profileImgEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ProfileFragment", "edit profile img button clicked");
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e("hey", ex.toString());
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(intent, 0);
                    }
                }            }
        });
        //il.displayImage("http://test-master-env-ecmnn89sfm.elasticbeanstalk.com.s3-website-us-west-1.amazonaws.com/public/beemo.png",profileImg);
       // il.displayImage(getDrawable(R.drawable.man), profileImg);
        profileImg.setImageResource(R.drawable.man);

        //save changes
        ImageButton saveButton = (ImageButton) rootView.findViewById(R.id.profile_save_button);
        saveButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try{
                    JSONObject json = new JSONObject();
                    json.put("password", passwordField.getText().toString());
                    json.put("email", emailField.getText().toString());
                    json.put("lastname", lastnameField.getText().toString());
                    json.put("firstname", firstnameField.getText().toString());
                    if(image_url.equals("default")){
                        image_url = "http://test-master-env-ecmnn89sfm.elasticbeanstalk.com.s3-website-us-west-1.amazonaws.com/public/beemo.png";
                    }
                    json.put("imageUrl", image_url);
                    //String ret = new MyHttpRequests().execute("/api/user/" + UserID.id, "PUT",
                    //       "{'password':'" + passwordField.getText().toString() + "', 'email':'" + emailField.getText().toString() + "','lastname':'" + lastnameField.getText().toString() + "', 'firstname':'" + firstnameField.getText().toString() +"'}").get();
                    String ret = new MyHttpRequests().execute("/api/user/" + UserID.id, "PUT", json.toString()).get();
                    Log.e("AN UPDATE WAS MADE PUT", ret);
                    if(!ret.equals("could not update user with id " + UserID.id)){
                        Toast prof_toast = Toast.makeText(getActivity().getApplicationContext(), "Successfully Saved Profile!", Toast.LENGTH_SHORT);
                        prof_toast.show();
                    }else{
                        Toast prof_toast = Toast.makeText(getActivity().getApplicationContext(), "Failed to Save Profile, Please Try Again", Toast.LENGTH_SHORT);
                        prof_toast.show();
                    }
                } catch(Exception e){
                    Log.e("ProfileFragment PUT", e.toString());
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        new UploadToImgurTask().execute(photoFile);
        try {
            JSONObject ret = new UploadToImgurTask().execute(photoFile).get();
            image_url = ret.getJSONObject("data").getString("link").replace("\\", "");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ImageLoader il = ImageLoader.getInstance();
        il.displayImage(image_url, profileImg);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
