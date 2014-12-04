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
import android.widget.Button;
import android.widget.CheckBox;
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

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.item_detail_popup, null);

        builder.setView(view);

        TextView titleView = (TextView)view.findViewById(R.id.item_detail_title);
        titleView.setText(title);

        TextView descriptionView = (TextView)view.findViewById(R.id.item_detail_description);
        descriptionView.setText(description);

        NumberFormat f = NumberFormat.getCurrencyInstance();
        TextView priceView = (TextView)view.findViewById(R.id.item_detail_price);
        priceView.setText(f.format(price));

        CheckBox oboBox = (CheckBox)view.findViewById(R.id.item_detail_obo);
        oboBox.setChecked(obo);

        final AlertDialog OptionDialog = builder.create();



        return OptionDialog;
    }

    public void setContent(String t, String d, int p, boolean o){
        title = t;
        description = d;
        price = p;
        obo = o;
    }
}
