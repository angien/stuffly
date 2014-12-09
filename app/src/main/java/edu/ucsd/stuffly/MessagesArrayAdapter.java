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

    public ArrayList<String> messages_textview1_text;
    public ArrayList<String> messages_textview2_text;
    public ArrayList<JSONObject> messages_json;
    public Image[] messages_imageview_image;


    public MessagesArrayAdapter(Activity context, ArrayList<String> text1,  ArrayList<String> text2, ArrayList<String> text3, ArrayList<JSONObject> json )//, Image[] image)
    {
        super(context, R.layout.cell_messages, text1);
        this.context = context;
        this.messages_textview1_text = text1;
        this.messages_textview2_text = text2;
        this.messages_json =json;
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
                String title = "";
                String description = "";
                int price = 0;
                boolean obo = false;
                String location = "";
                String picURL = "";

                try {
                    Log.e("GET VIEW ID USER", UserID.id);
                    text = messages_json.get(position).toString();
                    title = messages_json.get(position).getString("title");
                    description = messages_json.get(position).getString("description");
                    price = messages_json.get(position).getInt("price");
                    obo = messages_json.get(position).getBoolean("obo");
                    location = messages_json.get(position).getString("location");

                    ItemDetailFragment idf = new ItemDetailFragment();

                    idf.setContent(title,description, price,obo,location, picURL);

                    if ((UserID.id).equals(messages_json.get(position).getString("user"))) {
                        text = "MATCHES!!!";
                        idf.setSelf(true);
                    }
                    else {
                        idf.setSelf(false);
                    }
                    idf.show(((FragmentActivity) context).getSupportFragmentManager(), "itemDetail" + position);
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

        TextView messages_textview1 = (TextView) rowView.findViewById(R.id.messages_cell_title);
        messages_textview1.setText(this.messages_textview1_text.get(position));

        TextView messages_textview2 = (TextView) rowView.findViewById(R.id.messages_cell_description);
        messages_textview2.setText(this.messages_textview2_text.get(position));

        //ImageView messages_imageview = (ImageView) rowView.findViewById(R.id.messages_imageview);
        //messages_imageview.setImageResource(R.drawable.app_logo);



        return rowView;
    }
}
