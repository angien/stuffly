package edu.ucsd.stuffly;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ryanliao on 10/31/14.
 */
public class FeedFragment extends Fragment
{
    ArrayList<String> feed_list;
    ListView listview_feed;
    FeedArrayAdapter arrayAdapter;

    private ArrayList<String> text1;
    private ArrayList<String> text2;
    private ArrayList<JSONObject> json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        text1 = new ArrayList<String>();
        text2 = new ArrayList<String>();
        json = new ArrayList<JSONObject>();


        return rootView;
    }

    @Override
    public void onAttach (Activity a) {
//        MyHttpRequests rtaskget = new MyHttpRequests();
//        try{
//            rtaskget.execute("/api/post/","GET");
//            JSONArray feed = new JSONArray(rtaskget.get());
//
//            for(int i = 0; i < feed.length(); i++)
//            {
//                text1.add(i,feed.getJSONObject(i).getString("title"));
//                text2.add(i,feed.getJSONObject(i).getString("description"));
//                json.add(i,feed.getJSONObject(i));
//
//                Log.i("STUFFFFFF url", text1.get(i));
//                //text1[i] = "text1: " + Integer.toString(i);
//                //text2[i] = "text2: " + Integer.toString(i);
//            }
//
//
//        }catch(Exception e){
//            Log.e("FeedFragment GET", "treat yo self");
//        }
//
//
//        //feed_list = new ArrayList<String>();
//        //listview_feed = (ListView) getActivity().findViewById(R.id.listview_feed);
//        arrayAdapter = new FeedArrayAdapter(getActivity(), text1, text2, json);
//        listview_feed.setAdapter(arrayAdapter);

        super.onAttach(a);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        MyHttpRequests rtaskget = new MyHttpRequests();
        try{
            rtaskget.execute("/api/post/","GET");
            JSONArray feed = new JSONArray(rtaskget.get());

            for(int i = 0; i < feed.length(); i++)
            {
                text1.add(i,feed.getJSONObject(i).getString("title"));
                text2.add(i,feed.getJSONObject(i).getString("description"));
                json.add(i,feed.getJSONObject(i));

                Log.i("STUFFFFFF url", text1.get(i));
                //text1[i] = "text1: " + Integer.toString(i);
                //text2[i] = "text2: " + Integer.toString(i);
            }


        }catch(Exception e){
            Log.e("FeedFragment GET", "treat yo self");
        }


        feed_list = new ArrayList<String>();
        listview_feed = (ListView) getActivity().findViewById(R.id.listview_feed);
        arrayAdapter = new FeedArrayAdapter(getActivity(), text1, text2, json);
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