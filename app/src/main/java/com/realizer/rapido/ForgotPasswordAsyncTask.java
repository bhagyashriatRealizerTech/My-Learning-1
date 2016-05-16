package com.realizer.rapido;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sachin on 9/15/2015.
 */
public class ForgotPasswordAsyncTask extends AsyncTask<Void, Void,StringBuilder>
{
    //Declare controls
    ProgressDialog dialog;

    // Declare Variables
    StringBuilder resultPassword;
    String empId;
    Context myContxt;

    public ForgotPasswordAsyncTask(String empId,Context myContxt)
    {
        this.empId = empId;
        this.myContxt = myContxt;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=ProgressDialog.show(myContxt,"","Loading Data ...");
    }

    @Override
    protected StringBuilder doInBackground(Void... params) {

        resultPassword = new StringBuilder();
        String my = "http://104.217.254.180/RestWCF/svcEmp.svc/forgotPassword/" + empId;
        HttpGet httpGet = new HttpGet(my);
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line=reader.readLine()) != null)
                {
                    resultPassword.append(line);
                }
            }
            else
            {
                //Log.e("Error", "Failed to Reset Password");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            client.getConnectionManager().closeExpiredConnections();
            client.getConnectionManager().shutdown();
            dialog.dismiss();
        }
        return resultPassword;
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        dialog.dismiss();
    }
}
