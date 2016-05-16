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
 * Created by Suvarna.Ghoman on 20-07-2015.
 */
public class SwipeApplyAsyncTaskGet extends AsyncTask<Void, Void,StringBuilder>
{
    ProgressDialog dialog;
    StringBuilder resultLogin;
    String empId;
    Context myContext;
    String month;
    private AsyncTaskCompleteListener<String> callback;

    public SwipeApplyAsyncTaskGet(String _empId, String _month, Context _myContext,AsyncTaskCompleteListener<String> cb) {
        this.empId =_empId;
        this.myContext = _myContext;
        this.month = _month;
        this.callback = cb;    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=ProgressDialog.show(myContext,"","Loading Data ...");
    }

    @Override
    protected StringBuilder doInBackground(Void... params) {
        resultLogin = new StringBuilder();
        String week="1";
        //"7-27-2015,,Monday,,00:00_7-21-2015,,Tuesday,,05:06_7-22-2015,,Wednesday,,04:06_7-23-2015,,Thursday,,07:06_7-24-2015,,Friday,,05:06_7-25-2015,,Saturday,,02:06_7-26-2015,,Sunday,,05:06_7-28-2015,,Tuesday,,07:06_7-29-2015,,Wednesday,,06:06_7-30-2015,,Thursday,,-7358_7-31-2015,,Friday,,04:10"
        String my="http://104.217.254.180/RestWCF/svcEmp.svc/getEmpPendingSRRecords/"+ empId;
        HttpGet httpGet = new HttpGet(my);
        HttpClient client = new DefaultHttpClient();
        try
        {
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
               // Log.e("Error", "Failed to Login");
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
        callback.onTaskComplete(stringBuilder.toString(),5);
    }

}
