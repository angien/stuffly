package edu.ucsd.stuffly;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
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
    public Image[] feed_imageview_image;


    public FeedArrayAdapter(Activity context, String[] text1, String[] text2 )//, Image[] image)
    {
        super(context, R.layout.cell_feed, text1);
        this.context = context;
        this.feed_textview1_text = text1;
        this.feed_textview2_text = text2;
        //this.feed_imageview_image = image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cell_feed, parent, false);

        if(position % 2 == 1)
        {
            //LinearLayout bg = (LinearLayout) rowView.findViewById(R.id.cell_feed_linearlayout);
            //bg.setBackgroundColor(Color.parseColor("#e7e7e8")); // move this to @color later

        }

        String text1 = "text1 placeholder";
        String text2 = "text2 placeholder";
        //int imageId = getContext().getResources().getDrawable(R.id.app_logo);

        //TextView feed_textview1 = (TextView) rowView.findViewById(R.id.feed_textview1);
        //feed_textview1.setText(this.feed_textview1_text[position]);

        //TextView feed_textview2 = (TextView) rowView.findViewById(R.id.feed_textview2);
        //feed_textview2.setText(this.feed_textview2_text[position]);

        //ImageView feed_imageview = (ImageView) rowView.findViewById(R.id.feed_imageview);
        //feed_imageview.setImageResource(R.drawable.app_logo);



        return rowView;
    }
}

