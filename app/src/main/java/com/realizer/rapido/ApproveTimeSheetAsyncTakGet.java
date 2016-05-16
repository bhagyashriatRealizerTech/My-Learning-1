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
 * Created by Suvarna.Ghoman on 01-09-2015.
 */
public class ApproveTimeSheetAsyncTakGet extends AsyncTask<Void, Void,StringBuilder>
{
//Declare controls
        ProgressDialog dialog;

// Declare Variables
        StringBuilder resultLogin;
        String empId;
        Context myContext;

public ApproveTimeSheetAsyncTakGet(String empId, Context myContext) {
        this.empId = empId;
        this.myContext = myContext;
        }

@Override
protected void onPreExecute() {
        super.onPreExecute();
        dialog=ProgressDialog.show(myContext,"","loading Data ...");
        }

@Override
protected StringBuilder doInBackground(Void... params) {
        resultLogin = new StringBuilder();
        // Url to get leave details
        String my="http://104.217.254.180/RestWCF/svcEmp.svc/getTimeSheet/"+ empId;
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
        Log.e("Error", "Failed to Login");
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
        return resultLogin;
        }

@Override
protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        dialog.dismiss();
        }

        }

