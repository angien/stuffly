package edu.ucsd.stuffly;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by Angie on 12/7/14.
 */
public class MessagesArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;

    public ArrayList<String> date_array;
    public ArrayList<String> message_array;
    public ArrayList<JSONObject> json_array;
    public Image[] messages_imageview_image;


    public MessagesArrayAdapter(Activity context, ArrayList<String> text1,  ArrayList<String> text2, ArrayList<JSONObject> json )//, Image[] image)
    {
        super(context, R.layout.cell_messages, text1);
        this.context = context;
        this.date_array = text1;
        this.message_array = text2;
        this.json_array =json;
        //this.messages_imageview_image = image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cell_messages, parent, false);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                View root = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
                String text;
                String message;
                String date = "";
                String from_id = "";
                String from_name = "";

                try {
                    Log.e("GET VIEW ID USER", UserID.id);
                    text = json_array.get(position).toString();
                    message = message_array.get(position);
                    date = date_array.get(position);
                    from_name = json_array.get(position).getString("firstname") + " " + json_array.get(position).getString("lastname");
                    from_id = json_array.get(position).getString("_id");

                    MessageDetailFragment mdf = new MessageDetailFragment();

                    mdf.setContent(from_name, date, message, from_id);
                    mdf.show(((FragmentActivity) context).getSupportFragmentManager(), "itemDetail" + position);
                }
                catch (Exception e) {
                    text = e.toString();
                }
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        //int imageId = getContext().getResources().getDrawable(R.id.app_logo);


        try {
            TextView messages_textview1 = (TextView) rowView.findViewById(R.id.messages_cell_name);
            messages_textview1.setText(this.json_array.get(position).getString("firstname")+" "+ this.json_array.get(position).getString("lastname"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        TextView messages_textview2 = (TextView) rowView.findViewById(R.id.messages_cell_date);
        messages_textview2.setText(this.date_array.get(position));

        TextView messages_textview3 = (TextView) rowView.findViewById(R.id.messages_cell_message);
        messages_textview3.setText(this.message_array.get(position));

        //ImageView messages_imageview = (ImageView) rowView.findViewById(R.id.messages_imageview);
        //messages_imageview.setImageResource(R.drawable.app_logo);

        return rowView;
    }
}
