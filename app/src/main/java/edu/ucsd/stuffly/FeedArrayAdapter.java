package edu.ucsd.stuffly;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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

    public String[] feed_textview1_text;
    public String[] feed_textview2_text;
    public JSONObject[] feed_json;
    public Image[] feed_imageview_image;


    public FeedArrayAdapter(Activity context, String[] text1, String[] text2, JSONObject[] json )//, Image[] image)
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
                CharSequence text;
                try {
                    Log.e("GET VIEW ID USER", UserID.id);
                    if ((UserID.id).equals(feed_json[position].getString("user")))
                        text = "MATCHES!!!";
                    else
                        text = feed_json[position].toString();
                }
                catch (Exception e) {
                    text = "SOMETHING WRONG?";
                }
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        if(position % 2 == 1)
        {
            //LinearLayout bg = (LinearLayout) rowView.findViewById(R.id.cell_feed_linearlayout);
            //bg.setBackgroundColor(Color.parseColor("#e7e7e8")); // move this to @color later

        }

        String text1 = "text1 placeholder";
        String text2 = "text2 placeholder";
        //int imageId = getContext().getResources().getDrawable(R.id.app_logo);

        TextView feed_textview1 = (TextView) rowView.findViewById(R.id.feed_cell_title);
        feed_textview1.setText(this.feed_textview1_text[position]);

        TextView feed_textview2 = (TextView) rowView.findViewById(R.id.feed_cell_description);
        feed_textview2.setText(this.feed_textview2_text[position]);

        //ImageView feed_imageview = (ImageView) rowView.findViewById(R.id.feed_imageview);
        //feed_imageview.setImageResource(R.drawable.app_logo);



        return rowView;
    }
}

