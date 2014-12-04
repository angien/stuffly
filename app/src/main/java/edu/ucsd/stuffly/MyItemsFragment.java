package edu.ucsd.stuffly;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ryanliao on 10/31/14.
 */
public class MyItemsFragment extends Fragment
{
    ListView myItemsListView;
    ArrayList<String> items_list;
    FeedArrayAdapter arrayAdapter;
    Spinner spinner;

    private String[] text1;
    private String[] text2;
    private JSONObject[] json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View rootView = inflater.inflate(R.layout.fragment_myitems, container, false);
        text1 = new String[10];
        text2 = new String[10];
        json = new JSONObject[10];





        //spinner for choosing my stuff and my bids
        spinner = (Spinner) rootView.findViewById(R.id.myItemSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.my_items_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return rootView;



    }







    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
       /*
*/
        items_list = new ArrayList<String>();
        myItemsListView = (ListView) getActivity().findViewById(R.id.myItemslistView);
        arrayAdapter = new FeedArrayAdapter(getActivity(), text1, text2, json);
        myItemsListView.setAdapter(arrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                String uid = UserID.id;

                text1 = new String[10];
                text2 = new String[10];
                json = new JSONObject[10];

                if (item.toString().equals("My Items")) {
                    try {

                        JSONArray postArray = new JSONArray(new RetrieveFeedTask().execute("/api/user/" + uid + "/posts", "GET").get());

                        for (int i = 0; i < postArray.length(); i++) {
                            text1[i] = postArray.getJSONObject(i).getString("title");
                            text2[i] = postArray.getJSONObject(i).getString("description");
                            json[i] = postArray.getJSONObject(i);

                        }


                    } catch (Exception e) {
                        Log.e("MYITEMS GET2", e.toString());

                    }
                }
                else {
                    Log.e("SPINNER MOVED", item.toString());

                    try {

                        //JSONObject userInfo = new JSONObject(new RetrieveFeedTask().execute("/api/user/" + id, "GET").get());
                        //JSONArray postIDarray = userInfo.getJSONArray("posts");


                        for (int i = 0; i < 10; i++) {
                            //JSONObject postContent = new JSONObject(new RetrieveFeedTask().execute("/api/post/" + postIDarray.getString(i), "GET").get());
                            text1[i] = "whatever";
                            text2[i] = "whatever";
                            //text1[i] = "text1: " + Integer.toString(i);
                            //text2[i] = "text2: " + Integer.toString(i);
                            json[i] = new JSONObject();
                            Log.e("MY ITEMS", text1[i]);
                        }


                    } catch (Exception e) {
                        Log.e("MYITEMS GET2", e.toString());
                    }

                }
                arrayAdapter = new FeedArrayAdapter(getActivity(), text1, text2, json);
                myItemsListView.setAdapter(arrayAdapter);


//                myItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    ////
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        Context context = getContext();
//                        CharSequence text = ;
//                        TextField tv = (TextView)view.findViewById()
//                        int duration = Toast.LENGTH_SHORT;
//
//                        Toast toast = Toast.makeText(context, text, duration);
//                        toast.show();
//                    }
//
//
//                });

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });








        super.onActivityCreated(savedInstanceState);

    }

}
