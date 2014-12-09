package edu.ucsd.stuffly;




        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.ClipData;
        import android.content.ClipboardManager;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.facebook.widget.FacebookDialog;

        import org.json.JSONObject;

        import java.text.NumberFormat;

/**
 * Created by Angie on 12/8/14.
 */
public class MessageDetailFragment extends DialogFragment {
    String from = "";
    String from_id = "";
    String message = "";
    String date = "";

    EditText newMessage;
    NumberFormat f = NumberFormat.getCurrencyInstance();

    public MessageDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.message_detail_popup, null);

        builder.setView(view);

        TextView titleView = (TextView) view.findViewById(R.id.message_detail_from_name);
        titleView.setText(from);

        TextView priceView = (TextView) view.findViewById(R.id.message_detail_date);
        priceView.setText(date);

        TextView descriptionView = (TextView) view.findViewById(R.id.message_detail_message_text);
        descriptionView.setText(message);

        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.message_detail_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        newMessage = (EditText)view.findViewById(R.id.message_detail_reply_text);

        ImageButton send = (ImageButton) view.findViewById(R.id.message_detail_send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject post_json = new JSONObject();
                try {
                    post_json.put("to_id", from_id);
                    post_json.put("from_id", UserID.getUserId());
                    post_json.put("message", newMessage.getText().toString());
                }catch(Exception e){
                    Log.e("create post", "json error");
                }

                Log.e("post_json", post_json.toString());
                MyHttpRequests a = new MyHttpRequests();
                a.execute("/api/message", "POST", post_json.toString());
                try {
                    String ab = a.get();
                    Log.e("message reply .get()", ab);

                }catch (Exception e){
                    Log.e("error", e.toString());
                }
                dismiss();
            }
        });



        final AlertDialog OptionDialog = builder.create();

        return OptionDialog;
    }

    public void setContent(String f, String d, String m, String fid){
        from = f;
        date = d;
        message = m;
        from_id = fid;
    }

}
