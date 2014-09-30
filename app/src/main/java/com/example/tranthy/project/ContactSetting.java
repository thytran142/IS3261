package com.example.tranthy.project;
/* This class is to hanle the list of contacts user add to sms or email*/
import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.example.tranthy.project.AddManuallyContact.AddManuallyContactInterface;
import android.view.View;
import android.widget.Toast;

//Import library for the dialog
public class ContactSetting extends FragmentActivity
        implements AddManuallyContactInterface
{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_setting);


    }
    public void showAddManuallyDialog(View view){
        showAddManually();
    }
    private void showAddManually(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        AddManuallyContact addManuallyContact=new AddManuallyContact();
        addManuallyContact.setCancelable(false);
        addManuallyContact.setDialogTitle("Add Manually");
        addManuallyContact.show(getFragmentManager(),"input dialog");
    }
    @Override
    public void onFinishInputDialog(String inputText){
        //testing
        Toast.makeText(this,"Returned from dialog: "+inputText,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contact_setting_actions,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}


