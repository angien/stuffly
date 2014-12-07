package edu.ucsd.stuffly;



import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ItemDetailFragment extends DialogFragment {
    String title = "";
    String description = "";
    int price = 0;
    boolean obo = false;
    String category = "";
    String location = "Off-campus";
    boolean self = false;

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        NumberFormat f = NumberFormat.getCurrencyInstance();

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
        }

        final AlertDialog OptionDialog = builder.create();

        return OptionDialog;
    }

    public void setContent(String t, String d, int p, boolean o, String l){
        title = t;
        description = d;
        price = p;
        obo = o;
        location = l;
    }

    public void setSelf(boolean s){
        self = s;
    }
}
