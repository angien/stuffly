package edu.ucsd.stuffly;




        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.ClipData;
        import android.content.ClipboardManager;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.facebook.widget.FacebookDialog;

        import java.text.NumberFormat;

/**
 * Created by Angie on 12/8/14.
 */
public class MessageDetailFragment extends DialogFragment {
    String from = "";
    String message = "";
    String date = "";
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





        final AlertDialog OptionDialog = builder.create();

        return OptionDialog;
    }

    public void setContent(String f, String d, String m){
        from = f;
        date = d;
        message = m;
    }

}
