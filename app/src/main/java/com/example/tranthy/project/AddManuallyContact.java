package com.example.tranthy.project;

import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * This class is to handle the dialog add manually contacts and input to the contact_list sql database
 */
public class AddManuallyContact extends DialogFragment {
    EditText txt_contact_name;
    EditText txt_phone_number;
    EditText txt_contact_email;
    Button btn;
    Button btn_cancel;
    TextView nameError;
    TextView phoneError;
    TextView emailError;
    static String dialogTitle;

    //Interface containing methods to be implemented by calling activity
    public interface AddManuallyContactInterface {
        void submitContact(String name, String number, String email) throws SQLException;
    }

    //Empty constructor required
    public AddManuallyContact() {
    }

    public void setDialogTitle(String title) {
        dialogTitle = title;
    }

    private boolean isEmpty(EditText input) {
        if (input.getText().toString().trim().length() > 0) {
            return false;
        } else return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_contact_manually_dialog, container);
        //Get the edit text and button views
        txt_contact_name = (EditText) view.findViewById(R.id.txt_contact_name);
        txt_phone_number = (EditText) view.findViewById(R.id.txt_phone_number);
        txt_contact_email = (EditText) view.findViewById(R.id.txt_contact_email);
        nameError = (TextView) view.findViewById(R.id.nameError);
        phoneError = (TextView) view.findViewById(R.id.phoneError);

        btn = (Button) view.findViewById(R.id.btn_Done);
        //Event handler for the button
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //validate the empty string first
                if (isEmpty(txt_contact_name)) {
                    nameError.setText("This field cannot be empty!");
                } else if (isEmpty(txt_phone_number)) {
                    phoneError.setText("This field cannot be empty!");
                } else {
                    //get the calling activity
                    AddManuallyContactInterface activity = (AddManuallyContactInterface) getActivity();
                    try {
                        activity.submitContact(txt_contact_name.getText().toString(), txt_phone_number.getText().toString(), txt_contact_email.getText().toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //dismiss the alert
                    dismiss();
                }
            }
        });//end event handler for the button
        btn_cancel = (Button) view.findViewById(R.id.btn_Cancel_Manually);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });
        //show the keyboard automatically
        txt_contact_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //set the title for the dialog
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;

    }


}
