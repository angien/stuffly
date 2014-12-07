package edu.ucsd.stuffly;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

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
        final TextView firstnameField = (TextView) rootView.findViewById(R.id.profile_first_name);
        final TextView lastnameField = (TextView) rootView.findViewById(R.id.profile_last_name);
        final TextView emailField = (TextView) rootView.findViewById(R.id.profile_email);
        final TextView passwordField = (TextView) rootView.findViewById(R.id.profile_password);
        MyHTTPRequests rtaskget = new MyHTTPRequests();
        String id = UserID.id;
        try{
            //rtaskget.execute("/api/user/" + id,"GET");
            JSONObject userInfo = new JSONObject(rtaskget.execute("/api/user/" + id,"GET").get());
            Log.e("The returned string for profiel", userInfo.toString());

            email = userInfo.getString("email");
            password = userInfo.getString("password");
            firstName = userInfo.getString("firstname");
            lastName = userInfo.getString("lastname");
            //Log.i("STUFFFFFF url", "wat" + feed.get(i));

        }
        catch (JSONException e) {
            firstName = "";
            lastName = "";
        }
        catch(Exception e){
            Log.e("FeedFragment GET", e.toString());
        }
        firstnameField.setText(firstName);
        lastnameField.setText(lastName);
        emailField.setText(email);
        passwordField.setText(password);

        //on-campus or off-campus
        Spinner spinner = (Spinner) rootView.findViewById(R.id.profile_location_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.locations_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //save changes
        Button saveButton = (Button) rootView.findViewById(R.id.profile_save_button);
        saveButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try{
                    String ret = new MyHTTPRequests().execute("/api/user/" + UserID.id, "PUT",
                            "{'password':'" + passwordField.getText().toString() + "', 'email':'" + emailField.getText().toString() + "','lastname':'" + lastnameField.getText().toString() + "', 'firstname':'" + firstnameField.getText().toString() +"'}").get();
                    Log.e("AN UPDATE WAS MADE PUT", ret);
                } catch(Exception e){
                    Log.e("ProfileFragment PUT", e.toString());
                }
            }
        });

        return rootView;
    }
}
