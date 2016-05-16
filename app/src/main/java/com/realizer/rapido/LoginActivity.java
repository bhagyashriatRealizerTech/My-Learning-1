package com.realizer.rapido;

  import android.app.Activity;
  import android.app.ProgressDialog;
  import android.content.BroadcastReceiver;
  import android.content.Context;
  import android.content.Intent;
  import android.content.IntentFilter;
  import android.content.SharedPreferences;
  import android.net.ConnectivityManager;
  import android.net.NetworkInfo;
  import android.os.AsyncTask;
  import android.os.Handler;
  import android.os.StrictMode;
  import android.preference.PreferenceManager;
  import android.support.v7.app.ActionBarActivity;
  import android.os.Bundle;
  import android.util.Log;
  import android.view.View;
  import android.view.Window;
  import android.view.WindowManager;
  import android.widget.Button;
  import android.widget.CheckBox;
  import android.widget.EditText;
  import android.widget.Toast;

  import com.google.android.gcm.GCMRegistrar;
  import com.google.android.gms.gcm.Task;

  import java.util.concurrent.ExecutionException;
import static com.realizer.rapido.CommonUtilities.*;

public class LoginActivity extends Activity implements AsyncTaskCompleteListener<String>{
    // Declare controls
    ProgressDialog dg;
    EditText userName, password;
    Button loginButton;
    CheckBox checkBox;
    SharedPreferences sharedpreferences;
    ProgressDialog progress;
    // declare variables
    StringBuilder result;
    AsyncTask<Void, Void, Void> mRegisterTask;
    final Handler handler = new Handler();
    // Start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);

        setContentView(R.layout.login_layout);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Initialize controls
        userName = (EditText) findViewById(R.id.edtEmpId);
        password = (EditText) findViewById((R.id.edtPassword));
        loginButton = (Button) findViewById(R.id.btnLogin);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);

        //About Remember me in login page
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String getValueBack = sharedpreferences.getString("Username", "");
        userName.setText(getValueBack);

        String getValueBack1 = sharedpreferences.getString("Password", "");
        password.setText(getValueBack1);

    }

    public void LoginClick(View v) {
        boolean res = isConnectingToInternet();
        if(res==false)
        {
            Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

        }
        else {
            LoginAsyncTask obj = new LoginAsyncTask(userName.getText().toString(), password.getText().toString(), LoginActivity.this, this);
            obj.execute();
        }

    }

//Write code here  after getting result

    @Override
    public void onTaskComplete(String result,int pos) {

        String login = result.toString();

        if (login.contains("Manager")) {

            String[] loginDetails = login.split(",,");

            // setting global variables
            UserGlobalData.getInstance().setEmpId(userName.getText().toString());
            UserGlobalData.getInstance().setEmpType(loginDetails[0].toString());
            UserGlobalData.getInstance().setEmpFirstName(loginDetails[1].toString());
            UserGlobalData.getInstance().setEmpLastName(loginDetails[2].toString());
            UserGlobalData.getInstance().setCompanyCode(loginDetails[3].toString());
            UserGlobalData.getInstance().setCompanyLocation(loginDetails[4].toString());

            // to remove time part and replace forward slash
            if (loginDetails[5].equals("--"))
                UserGlobalData.getInstance().setEmpTimeIn(loginDetails[5].replace("\\", ""));
            else {
                String[] timeIn = loginDetails[5].split(" ");
                UserGlobalData.getInstance().setEmpTimeIn(timeIn[1].toString().replace("\\", ""));
            }
            if (loginDetails[6].equals("--"))
                UserGlobalData.getInstance().setEmpTimeIn(loginDetails[6].replace("\\", ""));
            else {
                String[] timeout = loginDetails[6].split(" ");
                UserGlobalData.getInstance().setEmpTimeOut(timeout[1].toString().replace("\\", ""));
            }
            GCMReg();
            String reset = loginDetails[7].replaceAll("\"", "");
            UserGlobalData.getInstance().setReset(reset);
            if (reset.equals("False")) {
                Intent iManager = new Intent(getApplicationContext(), MainActivityManager.class);
                startActivity(iManager);

            } else {
                Intent res = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(res);
            }
        } else if (login.contains("Employee")) {

            String[] loginDetails = login.split(",,");

            // setting global variables
            UserGlobalData.getInstance().setEmpId(userName.getText().toString());
            UserGlobalData.getInstance().setEmpType(loginDetails[0].toString());
            UserGlobalData.getInstance().setEmpFirstName(loginDetails[1].toString());
            UserGlobalData.getInstance().setEmpLastName(loginDetails[2].toString());
            UserGlobalData.getInstance().setCompanyCode(loginDetails[3].toString());
            UserGlobalData.getInstance().setCompanyLocation(loginDetails[4].toString());

            // to remove time part and replace forward slash
            if (loginDetails[5].equals("--"))
                UserGlobalData.getInstance().setEmpTimeIn(loginDetails[5].replace("\\", ""));
            else {
                String[] timeIn = loginDetails[5].split(" ");
                UserGlobalData.getInstance().setEmpTimeIn(timeIn[1].toString().replace("\\", ""));
            }
            if (loginDetails[6].equals("--"))
                UserGlobalData.getInstance().setEmpTimeIn(loginDetails[6].replace("\\", ""));
            else {
                String[] timeout = loginDetails[6].split(" ");
                UserGlobalData.getInstance().setEmpTimeOut(timeout[1].toString().replace("\\", ""));
            }
            GCMReg();
            String reset = loginDetails[7].replaceAll("\"", "");
            UserGlobalData.getInstance().setReset(reset);
            if (reset.equals("True")) {
                Intent res = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(res);
            } else {
                Intent iEmp = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iEmp);
            }
        } else if (userName.getText().toString().equals("") && password.getText().toString().equals("")) {
            //progress.dismiss();
            Toast.makeText(getApplicationContext(), "Please Enter Username/Password!!!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Invalid credentials, Pls Try again!!!", Toast.LENGTH_LONG).show();
        }



    if (checkBox.isChecked()) {
        SharedPreferences.Editor edit = sharedpreferences.edit();
        edit.putString("Username", userName.getText().toString().trim());
        edit.putString("Password", password.getText().toString().trim());
        edit.commit();

    }
}



    public void  ForgotPswd(View v)
    {
        Intent pswd = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(pswd);
    }

    public void contactUs(View v)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ContactUs@realizertech.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.putExtra(Intent.EXTRA_TEXT   , "HI");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LoginActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void GCMReg()
    {

        registerReceiver(mHandleMessageReceiver,
                new IntentFilter(DISPLAY_MESSAGE_ACTION));
        GCMRegistrar.register(LoginActivity.this, SENDER_ID);

    }



    private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
                    Toast.makeText(context,newMessage,Toast.LENGTH_SHORT).show();

                }
            };

    public boolean isConnectingToInternet(){

        ConnectivityManager connectivity =
                (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }


}









