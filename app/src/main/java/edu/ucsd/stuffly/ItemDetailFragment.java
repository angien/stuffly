package edu.ucsd.stuffly;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
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

import java.text.NumberFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ItemDetailFragment extends DialogFragment {
    String title = "";
    String description = "";
    double price = 0;
    boolean obo = false;
    String category = "";
    String location = "Off-campus";
    boolean self = false;
    String picURL = "";
    NumberFormat f = NumberFormat.getCurrencyInstance();

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ImageLoader il = ImageLoader.getInstance();

        if(!self) {
            View view = inflater.inflate(R.layout.item_detail_popup, null);

            builder.setView(view);

            TextView titleView = (TextView) view.findViewById(R.id.item_detail_title);
            titleView.setText(title);

            TextView descriptionView = (TextView) view.findViewById(R.id.item_detail_description);
            descriptionView.setText(description);

            TextView priceView = (TextView) view.findViewById(R.id.item_detail_price);
            priceView.setText(f.format(price));

            CheckBox oboBox = (CheckBox) view.findViewById(R.id.item_detail_obo);
            oboBox.setChecked(obo);

            TextView locationView = (TextView) view.findViewById(R.id.item_detail_location);
            locationView.setText(location);

            ImageView imageView = (ImageView) view.findViewById(R.id.item_detail_pic);
            il.displayImage(picURL,imageView);

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
        }else{
            View view = inflater.inflate(R.layout.item_detail_popup_editable, null);

            builder.setView(view);

            EditText titleEdit = (EditText) view.findViewById(R.id.item_detail_title_editable);
            titleEdit.setText(title);

            EditText descriptionEdit = (EditText) view.findViewById(R.id.item_detail_description_editable);
            descriptionEdit.setText(description);

            EditText priceEdit = (EditText) view.findViewById(R.id.item_detail_price_editable);
            priceEdit.setText(f.format(price));

            CheckBox oboEdit = (CheckBox) view.findViewById(R.id.item_detail_obo_editable);
            oboEdit.setChecked(obo);

            Spinner locationEdit = (Spinner) view.findViewById(R.id.item_detail_location_editable);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
            R.array.locations_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            locationEdit.setAdapter(adapter);
            locationEdit.setSelection(adapter.getPosition(location));

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
        }

        final AlertDialog OptionDialog = builder.create();

        return OptionDialog;
    }

    public void setContent(String t, String d, double p, boolean o, String l, String u){
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
            String text = "I posted this for sale:\n\n"
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
                    .setLink(null)
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
