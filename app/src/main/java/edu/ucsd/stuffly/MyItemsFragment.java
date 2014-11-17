package edu.ucsd.stuffly;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.ucsd.stuffly.R;

/**
 * Created by ryanliao on 10/31/14.
 */
public class MyItemsFragment extends Fragment
{
    ListView myItemsListView;
    ArrayList<String> items_list;
    FeedArrayAdapter arrayAdapter;

    private String[] text1;
    private String[] text2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_myitems, container, false);

        //spinner for choosing my stuff and my bids
        Spinner spinner = (Spinner) rootView.findViewById(R.id.myItemSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.my_items_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //list view for items
        items_list = new ArrayList<String>();
        myItemsListView = (ListView) rootView.findViewById(R.id.myItemslistView);
        text1 = new String[10];
        text2 = new String[10];
        for(int i = 0; i < 10; i++)
        {
            text1[i] = "text1: " + Integer.toString(i);
            text2[i] = "text2: " + Integer.toString(i);
        }
        arrayAdapter = new FeedArrayAdapter(getActivity(), text1, text2);
        myItemsListView.setAdapter(arrayAdapter);

        return rootView;
    }

}
