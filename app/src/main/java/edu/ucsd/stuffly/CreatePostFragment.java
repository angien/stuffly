package edu.ucsd.stuffly;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CreatePostFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_post_popup, null);

        builder.setView(view);

        final AlertDialog OptionDialog = builder.create();
        //doesn't work
        //OptionDialog.setCanceledOnTouchOutside(true);
        Button cancelButton = (Button)view.findViewById(R.id.stuff_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionDialog.dismiss();
            }
        });

        return OptionDialog;
    }
}
