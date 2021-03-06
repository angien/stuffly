package edu.ucsd.stuffly;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ItemDetailFragment extends DialogFragment {
    String postId = "";
    String posterId = "";
    String name = "";
    String title = "";
    String description = "";
    double price = 0;
    boolean obo = false;
    String category = "";
    String location = "Off-campus";
    boolean self = false;
    String picURL = "";
    NumberFormat f = NumberFormat.getCurrencyInstance();
    JSONObject put_json;
    EditText newMessage;

    Spinner loc_spinner;
    Spinner cat_spinner;
    Spinner cond_spinner;
    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ImageLoader il = ImageLoader.getInstance();

        if(!self) { // not your own!
            View view = inflater.inflate(R.layout.item_detail_popup, null);

            builder.setView(view);

            TextView titleView = (TextView) view.findViewById(R.id.item_detail_title);
            titleView.setText(title);

            TextView postedByView = (TextView) view.findViewById(R.id.item_detail_posted_by);
            postedByView.setText("Posted by " + name);

            TextView descriptionView = (TextView) view.findViewById(R.id.item_detail_description);
            descriptionView.setText(description);

            TextView priceView = (TextView) view.findViewById(R.id.item_detail_price);
            priceView.setText(f.format(price));

            CheckBox oboBox = (CheckBox) view.findViewById(R.id.item_detail_obo);
            oboBox.setChecked(obo);

            TextView locationView = (TextView) view.findViewById(R.id.item_detail_location);
            locationView.setText(location);

            ImageView imageView = (ImageView) view.findViewById(R.id.item_detail_pic);
            il.displayImage(picURL, imageView);

//            Button shareButton = (Button) view.findViewById(R.id.item_detail_facebook_button);
//            shareButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    facebookShare(title, description, price, obo, location);
//                }
//            });

            ImageButton cancelButton = (ImageButton) view.findViewById(R.id.item_detail_cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            newMessage = (EditText) view.findViewById(R.id.item_detail_message_text);

            ImageButton messageButton = (ImageButton) view.findViewById(R.id.item_detail_message_button);
            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject post_json = new JSONObject();
                    try {
                        post_json.put("to_id", posterId);
                        post_json.put("from_id", UserID.getUserId());
                        post_json.put("message", newMessage.getText().toString());
                    }catch(Exception e){
                        Log.e("create post", "json error");
                    }

                    Log.e("post_json", post_json.toString());
                    MyHttpRequests a = new MyHttpRequests();
                    a.execute("/api/message", "POST", post_json.toString());
                    try {
                        String ab = a.get();
                        Log.e("message reply .get()", ab);

                    }catch (Exception e){
                        Log.e("error", e.toString());
                    }
                    dismiss();
                    // toast saying that it sent?
                    dismiss();

                }
            });




        }else{
            View view = inflater.inflate(R.layout.item_detail_popup_editable, null);
            put_json = new JSONObject();
            builder.setView(view);

            final EditText titleEdit = (EditText) view.findViewById(R.id.item_detail_title_editable);
            titleEdit.setText(title);

            TextView postedByView = (TextView) view.findViewById(R.id.item_detail_editable_posted_by);
            postedByView.setText("Posted by " + name);

            final EditText descriptionEdit = (EditText) view.findViewById(R.id.item_detail_description_editable);
            descriptionEdit.setText(description);

            final EditText priceEdit = (EditText) view.findViewById(R.id.item_detail_price_editable);
            priceEdit.setText(f.format(price));
            priceEdit.setRawInputType(Configuration.KEYBOARD_12KEY);
            priceEdit.addTextChangedListener(new TextWatcher(){
                private String current = "";

                @Override
                public void afterTextChanged(Editable arg0) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().equals(current)){
                        priceEdit.removeTextChangedListener(this);

                        String cleanString = s.toString().replaceAll("[$,.]", "");

                        double parsed = Double.parseDouble(cleanString);
                        String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                        current = formatted;
                        priceEdit.setText(formatted);
                        priceEdit.setSelection(formatted.length());

                        priceEdit.addTextChangedListener(this);
                    }
                }
            });

            final CheckBox oboEdit = (CheckBox) view.findViewById(R.id.item_detail_obo_editable);
            oboEdit.setChecked(obo);

            final ImageView imageViewEdit = (ImageView) view.findViewById(R.id.item_detail_pic_editable);
            il.displayImage(picURL,imageViewEdit);

            final Spinner locationEdit = (Spinner) view.findViewById(R.id.item_detail_location_editable);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
            R.array.locations_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            locationEdit.setAdapter(adapter);
            locationEdit.setSelection(adapter.getPosition(location));

            final Spinner categoryEdit = (Spinner) view.findViewById(R.id.item_detail_category_editable);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.category_array, android.R.layout.simple_spinner_dropdown_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoryEdit.setAdapter(adapter2);
            categoryEdit.setSelection(adapter2.getPosition(location));

            final Spinner conditionEdit = (Spinner) view.findViewById(R.id.item_detail_condition_editable);
            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                    R.array.condition_array, android.R.layout.simple_spinner_dropdown_item);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            conditionEdit.setAdapter(adapter3);
            conditionEdit.setSelection(adapter3.getPosition(location));

            Button shareButton = (Button) view.findViewById(R.id.item_detail_editable_facebook_button);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    facebookShare(title, description, price, obo, location);
                }
            });


            ImageButton cancelButton = (ImageButton) view.findViewById(R.id.item_detail_editable_cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            ImageButton deleteButton = (ImageButton) view.findViewById(R.id.item_detail_editable_delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyHttpRequests a = new MyHttpRequests();
                    a.execute("/api/post/" + postId, "DELETE");
                    dismiss();
                }
            });

            ImageButton saveButton = (ImageButton) view.findViewById(R.id.item_detail_editable_save_button);
            saveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try{
                        put_json.put("user", UserID.getUserId());
                        put_json.put("title", titleEdit.getText().toString());
                        put_json.put("imageUrl", picURL);
                        put_json.put("description", descriptionEdit.getText().toString());
                        put_json.put("price", NumberFormat.getInstance().parse(priceEdit.getText().toString().substring(1)));//Double.parseDouble(priceEdit.getText().toString().substring(1)));
                        put_json.put("category", "Electronics");
                        put_json.put("location", locationEdit.getSelectedItem().toString());
                        put_json.put("condition", "Acceptable");
                        put_json.put("obo", Boolean.toString(oboEdit.isChecked()));
                        Log.e("JSON", put_json.toString());
                    } catch(Exception e){
                        Log.e("ItemDetailFragment PUT", e.toString());
                    }

                    Log.e("better work ItemDetailFragment PUT", put_json.toString());

                    MyHttpRequests a = new MyHttpRequests();
                    a.execute("/api/post/" + postId, "PUT", put_json.toString());
                    try {
                        String ab = a.get();
                        Log.e("item details put a.get()", ab);

                    }catch (Exception e){
                        Log.e("error", e.toString());
                    }
                    dismiss();
                }
            });
        }

        final AlertDialog OptionDialog = builder.create();

        return OptionDialog;
    }

    public void setContent(String id, String n, String poster_id, String t, String d, double p, boolean o, String l, String u){
        postId = id;
        posterId = poster_id;
        name = n;
        title = t;
        description = d;
        price = p;
        obo = o;
        location = l;
        picURL = u;
    }

    public void setSelf(boolean s){
        self = s;
    }


    public void facebookShare(String t, String d, double p, boolean o, String l) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, "http://stuffly.herokuapp.com/");
//// See if official Facebook app is found
//        boolean facebookAppFound = false;
//        List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
//        for (ResolveInfo info : matches) {
//            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook")) {
//                intent.setPackage(info.activityInfo.packageName);
//                facebookAppFound = true;
//                break;  }}
//
//        if(facebookAppFound) {

            int duration = Toast.LENGTH_LONG;
            String text = "I posted this for sale on Stuffly:\n\n"
                    + title + "\n"
                    + description + "\n\n"
                    + "for " + f.format(price)
                    + (obo ? " OBO. " : ". ")
                    + location + ".";
            Toast toast = Toast.makeText(getActivity(),"Copied post to clipboard. Paste in Facebook!",duration);
            toast.show();

            ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Stuffly share", text);
            clipboard.setPrimaryClip(clip);

            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                    .setLink(picURL)
                    .setName(title)
                    .setDescription(description + "\n\n"
                            + f.format(price)
                            + (obo ? " OBO. " : ". ")
                            + location + ".")
                    .build();
            MainActivity.uiHelper.trackPendingDialogCall(shareDialog.present());
//            return;
//        }
//
//// As fallback, launch sharer.php in a browser
//        else if (!facebookAppFound) {
//            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + "http://stuffly.herokuapp.com/";
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
//        }
//        startActivity(intent);


    }
}
