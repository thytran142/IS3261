package com.example.tranthy.project;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by tranthy on 26/10/14.
 */
public class Help extends DialogFragment {
    static String dialogTitle;
    Button btn;
    public Help(){}
    public interface HelpInterface{

    }
    public void setDialogTitle(String title){
        dialogTitle=title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.help,container);

        btn=(Button)view.findViewById(R.id.btn_help_OK);
        //Event handler for the button
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                    //dismiss the alert
                    dismiss();}

        });//end event handler for the button

        //set the title for the dialog
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
      return view;
 }
}
