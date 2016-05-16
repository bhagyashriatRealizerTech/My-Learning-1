package com.realizer.rapido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;



/**
 * Created by sachin on 9/15/2015.
 */
public class ForgotPasswordActivity extends Activity {

    Intent intent;
    EditText username;
    Button send;
    StringBuilder result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);
        controls();
        intent = getIntent();
    }

    public void resetPassword(View v)
    {
        if (username.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter username...", Toast.LENGTH_LONG).show();
        }
        else {
            String empId = username.getText().toString();
            ForgotPasswordAsyncTask obj = new ForgotPasswordAsyncTask(empId, ForgotPasswordActivity.this);
            try {
                result = obj.execute().get();
                String forgotresult = result.toString().replace("\"", "");
               // Log.d("forgotresult=", forgotresult);
                if (forgotresult.equalsIgnoreCase("true")) {
                    Toast.makeText(getApplicationContext(), "Applied successfully", Toast.LENGTH_LONG).show();
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                } else
                    Toast.makeText(getApplicationContext(), "Server not responding please try again after sometime.", Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                obj.cancel(isFinishing());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_switch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void controls() {

        username=(EditText)findViewById(R.id.forgetusername);
        send=(Button)findViewById(R.id.btnsend);
    }
}


