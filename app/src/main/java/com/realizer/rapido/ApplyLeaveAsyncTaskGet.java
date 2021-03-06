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
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Suvarna.Ghoman on 20-07-2015.
 */
public class ApplyLeaveAsyncTaskGet extends AsyncTask<Void, Void,StringBuilder>
{
    //Declare controls
    ProgressDialog dialog;
  private AsyncTaskCompleteListener<String>callback;
    // Declare Variables
    StringBuilder resultLogin;
    String empId;
    Context myContext;
    int pos;

    public ApplyLeaveAsyncTaskGet(String empId, Context myContext,AsyncTaskCompleteListener<String> cb,int pos) {
        this.empId = empId;
        this.myContext = myContext;
        this.callback = cb;
        this.pos = pos;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=ProgressDialog.show(myContext,"","Loading Data ...");
    }

    @Override
    protected StringBuilder doInBackground(Void... params) {
        resultLogin = new StringBuilder();
        // Url to get leave details
        String my="http://104.217.254.180/RestWCF/svcEmp.svc/getEmpLvBalance/"+ empId;

        HttpClient client = new DefaultHttpClient();
        try
        {
            HttpGet httpGet = new HttpGet(my);
            BasicResponseHandler responseHandler = new BasicResponseHandler();
            String responseBody = client.execute(httpGet, responseHandler);
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
                    resultLogin.append(line);
                }
            }
            else
            {
                //Log.e("Error", "Failed to Login");
            }
        }
        catch(ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            client.getConnectionManager().closeExpiredConnections();
            client.getConnectionManager().shutdown();

        }
        return resultLogin;
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        dialog.dismiss();

       callback.onTaskComplete(stringBuilder.toString(),pos);
    }

}
