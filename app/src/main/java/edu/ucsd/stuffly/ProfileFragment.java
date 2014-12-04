package edu.ucsd.stuffly;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.ucsd.stuffly.R;

/**
 * Created by ryanliao on 10/31/14.
 */
public class ProfileFragment extends Fragment
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView nameField = (TextView) rootView.findViewById(R.id.profile_name);
        TextView emailField = (TextView) rootView.findViewById(R.id.profile_email);
        TextView passwordField = (TextView) rootView.findViewById(R.id.profile_password);
        RetrieveFeedTask rtaskget = new RetrieveFeedTask();
        String id = UserID.id;
        try{
            //rtaskget.execute("/api/user/" + id,"GET");
            JSONObject userInfo = new JSONObject(rtaskget.execute("/api/user/" + id,"GET").get());
            firstName = userInfo.getString("firstname");
            lastName = userInfo.getString("lastname");
            email = userInfo.getString("email");
            password = userInfo.getString("password");
            //Log.i("STUFFFFFF url", "wat" + feed.get(i));

        }catch(Exception e){
            Log.e("FeedFragment GET", "treat yo self");
        }
        nameField.setText(firstName+ " " +lastName);
        emailField.setText(email);
        passwordField.setText(password);


        //on-campus or off-campus
        Spinner spinner = (Spinner) rootView.findViewById(R.id.profile_location_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.locations_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return rootView;
    }
}
