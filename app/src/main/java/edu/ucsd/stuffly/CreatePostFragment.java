package edu.ucsd.stuffly;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatePostFragment extends DialogFragment {

    JSONObject post_json;
    EditText title_et;
    EditText description_et;
    EditText price_et;
    Switch obo_switch;
    Spinner loc_spinner;
    Spinner cond_spinner;
    Spinner cate_spinner;
    ImageButton img_btn;
    Button stuffit_btn;
    File photoFile;
    String mCurrentPhotoPath;

    Uri imageUri;
    boolean obo_bool;
    Bitmap bitmap;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_post_popup, null);
        builder.setView(view);

        obo_bool = false;
        post_json = new JSONObject();
        final AlertDialog OptionDialog = builder.create();
        //doesn't work
        //OptionDialog.setCanceledOnTouchOutside(true);

        title_et = (EditText)view.findViewById(R.id.stuff_title);
        description_et = (EditText)view.findViewById(R.id.stuff_description);
        price_et = (EditText)view.findViewById(R.id.stuff_price);
        obo_switch = (Switch)view.findViewById(R.id.stuff_obo);
        obo_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                obo_bool = isChecked;
            }
        });

        loc_spinner = (Spinner) view.findViewById(R.id.stuff_location);
        ArrayAdapter<CharSequence> loc_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.locations_array, android.R.layout.simple_spinner_dropdown_item);
        loc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc_spinner.setAdapter(loc_adapter);

        cate_spinner = (Spinner)view.findViewById(R.id.stuff_category);
        ArrayAdapter<CharSequence> cate_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.category_array, android.R.layout.simple_spinner_dropdown_item);
        cate_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cate_spinner.setAdapter(cate_adapter);

        cond_spinner = (Spinner)view.findViewById(R.id.stuff_condition);
        ArrayAdapter<CharSequence> cond_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.condition_array, android.R.layout.simple_spinner_dropdown_item);
        cond_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cond_spinner.setAdapter(cond_adapter);

        img_btn = (ImageButton)view.findViewById(R.id.stuff_pic_button);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }
            }
        });

        //        Post
//        {
//            _id: ID,
//                    user: User’s ID, // NEED FOR POST REQUEST, This is the user who made the post
//            title: String, // NEED FOR POST REQUEST
//                    description: String, // NEED FOR POST REQUEST
//                price: Number, // NEED FOR POST REQUEST
//                category: String, // NEED FOR POST REQUEST
//                location: String, // NEED FOR POST REQUEST
//                offers: [ Offer ID’s ]
//            condition: String, // NEED FOR POST REQUEST, “Like New”, “Good”, “Acceptable”
//                    imageUrl: String, // NOT IMPLEMENTED YET
//                obo: String,´ // NEEDED - “true” or “false”
//            created: Date (Represented in ISODate format in MongoDB), AUTOMATIC
//            updated: Date ← AUTOMATICALLY GENERATED TOO
//        }
        stuffit_btn = (Button)view.findViewById(R.id.stuff_it_button);
        stuffit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    post_json.put("user", UserID.getUserId());
                    post_json.put("title", title_et.getText().toString());
                    post_json.put("description", description_et.getText().toString());
                    post_json.put("price", Double.parseDouble(price_et.getText().toString()));
                    post_json.put("category", cate_spinner.getSelectedItem().toString());
                    post_json.put("location", loc_spinner.getSelectedItem().toString());
                    post_json.put("condition", cond_spinner.getSelectedItem().toString());
                    post_json.put("obo", obo_bool);
                    post_json.put("imageUrl", "http://vignette1.wikia.nocookie.net/pokemon/images/6/64/025Pikachu_AG_anime_4.png/revision/latest?cb=20140410200756");
                }catch(Exception e){
                    Log.e("create post", "json error");
                }

                Log.e("post_json", post_json.toString());
                MyHttpRequests a = new MyHttpRequests();
                a.execute("/api/post", "POST", post_json.toString());
                try {
                    String ab = a.get();
                    Log.e("a.get()", ab);

                }catch (Exception e){
                    Log.e("error", e.toString());
                }

            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.stuff_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionDialog.dismiss();
            }
        });

        return OptionDialog;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bp = (Bitmap) data.getExtras().get("data");
  //      img_btn.setImageBitmap(bp);


            new UploadToImgurTask().execute(photoFile);

//        AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( MY_ACCESS_KEY_ID, MY_SECRET_KEY ) );
//        s3Client.createBucket( MY_PICTURE_BUCKET );
//        PutObjectRequest por = new PutObjectRequest( Constants.getPictureBucket(), Constants.PICTURE_NAME, new java.io.File( filePath) );
//        s3Client.putObject( por );



    }


}
