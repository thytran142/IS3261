package com.example.tranthy.project;

import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
/**
 * This class is to handle the dialog add manually contacts and input to the contact_list sql database
 */
public class AddManuallyContact extends DialogFragment {
    EditText txt_contact_name;
    Button btn;
    static String dialogTitle;
 //Interface containing methods to be implemented by calling activity
    public interface AddManuallyContactInterface{
        void onFinishInputDialog(String inputText);
    }
    //Empty constructor required
    public AddManuallyContact(){}
    public void setDialogTitle(String title){
        dialogTitle=title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.add_contact_manually_dialog,container);
        //Get the edit text and button views
        txt_contact_name= (EditText)view.findViewById(R.id.txt_contact_name);
        btn=(Button)view.findViewById(R.id.btn_Done);
        //Event handler for the button
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //get the calling activity
                AddManuallyContactInterface activity= (AddManuallyContactInterface)getActivity();
                activity.onFinishInputDialog(txt_contact_name.getText().toString());
                //dismiss the alert
                dismiss();
            }
        });//end event handler for the button
        //show the keyboard automatically
        txt_contact_name.requestFocus();
        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //set the title for the dialog
        getDialog().setTitle(dialogTitle);
        return view;

    }

}
