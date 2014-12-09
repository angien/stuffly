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
 * Created by ryanliao on 11/1/14.
 */
public class FeedArrayAdapter extends ArrayAdapter<String>
{
    private final Activity context;

    public ArrayList<String> feed_textview1_text;
    public ArrayList<String> feed_textview2_text;
    public ArrayList<JSONObject> feed_json;
    public Image[] feed_imageview_image;


    public FeedArrayAdapter(Activity context, ArrayList<String> text1, ArrayList<String> text2, ArrayList<JSONObject> json )//, Image[] image)
    {
        super(context, R.layout.cell_feed, text1);
        this.context = context;
        this.feed_textview1_text = text1;
        this.feed_textview2_text = text2;
        this.feed_json =json;
        //this.feed_imageview_image = image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cell_feed, parent, false);

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
                String id ="";

                try {
                    Log.e("GET VIEW ID USER", UserID.id);
                    id = feed_json.get(position).getString("_id");
                    text = feed_json.get(position).toString();
                    title = feed_json.get(position).getString("title");
                    description = feed_json.get(position).getString("description");
                    price = feed_json.get(position).getInt("price");
                    obo = feed_json.get(position).getBoolean("obo");
                    location = feed_json.get(position).getString("location");

                    ItemDetailFragment idf = new ItemDetailFragment();

                    idf.setContent(id,title,description, price,obo,location);

                    if ((UserID.id).equals(feed_json.get(position).getString("user"))) {
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

        TextView feed_textview1 = (TextView) rowView.findViewById(R.id.feed_cell_title);
        feed_textview1.setText(this.feed_textview1_text.get(position));

        TextView feed_textview2 = (TextView) rowView.findViewById(R.id.feed_cell_description);
        feed_textview2.setText(this.feed_textview2_text.get(position));

        //ImageView feed_imageview = (ImageView) rowView.findViewById(R.id.feed_imageview);
        //feed_imageview.setImageResource(R.drawable.app_logo);



        return rowView;
    }
}

