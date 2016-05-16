package com.realizer.rapido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by admin on 15/10/2015.
 */
public class ResetPasswordActivity extends Activity {
    Intent intent;
    EditText username,newpassword,confirmpassword;
    Button changepassword;
    StringBuilder result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        controls();

    }
    public void changePassword(View v)
    {
        intent = getIntent();
        if (username.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter temporary password...", Toast.LENGTH_LONG).show();
        }
        else if (newpassword.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter new password...", Toast.LENGTH_LONG).show();
        }
        else if (confirmpassword.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter confirm password...", Toast.LENGTH_LONG).show();
        }
        else {
            final String empId=UserGlobalData.getInstance().getEmpId();
            String newpswd = newpassword.getText().toString();
            String confirmpswd = confirmpassword.getText().toString();
            ResetPasswordAsyncGet obj = new ResetPasswordAsyncGet(empId, newpswd, confirmpswd, ResetPasswordActivity.this);
            try {//http://104.217.254.180/RestWCF/svcEmp.svc/forgotPassword/AP-14
                result = obj.execute().get();
                String forgotresult = result.toString().replace("\"", "");
                if (forgotresult.equalsIgnoreCase("true")) {
                    Toast.makeText(getApplicationContext(), "Change Password successfully", Toast.LENGTH_LONG).show();
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

    public void controls() {

        username=(EditText)findViewById(R.id.resetusername);
        newpassword=(EditText)findViewById(R.id.newpassword);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        changepassword=(Button)findViewById(R.id.btnchange);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_switch, menu);
        return true;
    }

}
