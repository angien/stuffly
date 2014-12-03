package edu.ucsd.stuffly;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import edu.ucsd.stuffly.R;

/**
 * Created by ryanliao on 10/31/14.
 */
public class FeedFragment extends Fragment
{
    ArrayList<String> feed_list;
    ListView listview_feed;
    FeedArrayAdapter arrayAdapter;

    private String[] text1;
    private String[] text2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        text1 = new String[10];
        text2 = new String[10];


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        RetrieveFeedTask rtaskget = new RetrieveFeedTask();
        RetrieveFeedTask rtaskpost = new RetrieveFeedTask();
        try{
            rtaskget.execute("http://test-master-env-ecmnn89sfm.elasticbeanstalk.com/api/user/","GET");
            JSONArray feed = new JSONArray(rtaskget.get());

            for(int i = 0; i < 10; i++)
            {
                text1[i] = feed.getJSONObject(0).getString("firstname") + " " + i;
                text2[i] = feed.getJSONObject(0).getString("lastname") + " " + i;
                Log.i("STUFFFFFF url", text1[i]);
                //text1[i] = "text1: " + Integer.toString(i);
                //text2[i] = "text2: " + Integer.toString(i);
            }


        }catch(Exception e){
            Log.e("FeedFragment GET", "treat yo self");
        }



        try{
            rtaskpost.execute("http://test-master-env-ecmnn89sfm.elasticbeanstalk.com/api/user/","POST");
        }catch(Exception e){
            Log.e("FeedFragment POST", e.toString());
        }



        feed_list = new ArrayList<String>();
        listview_feed = (ListView) getActivity().findViewById(R.id.listview_feed);
        arrayAdapter = new FeedArrayAdapter(getActivity(), text1, text2);
        listview_feed.setAdapter(arrayAdapter);

        super.onActivityCreated(savedInstanceState);





//        // allow action when clicking on listview's cell
//        listview_feed.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            ////
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                feed_list.remove(position);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//
//        });
    }


}