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
import java.util.Calendar;

/**
 * Created by Suvarna.Ghoman on 18-08-2015.
 */
public class HolidayListAsyncTaskGet  extends AsyncTask<Void, Void,StringBuilder>
    {
        //Declare controls
        ProgressDialog dialog;

        // Declare Variables
        StringBuilder resultHolidayList;
        String location;
        String companyCode;
        Context myContext;
        String year;
        private AsyncTaskCompleteListener<String> callback;

        public HolidayListAsyncTaskGet(String _location, String _companyCode, String _year,Context myContext,AsyncTaskCompleteListener<String> cb) {
            this.location = _location;
            this.myContext = myContext;
            this.companyCode=_companyCode;
            this.year = _year;
            this.callback = cb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=ProgressDialog.show(myContext,"","Loading Data ...");
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            resultHolidayList = new StringBuilder();

            // Url to get leave details
            String my="http://104.217.254.180/RestWCF/svcEmp.svc/getCompanyPublicHolidays/"+companyCode+"/"+location+"/"+year;

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
                        resultHolidayList.append(line);
                    }
                }
                else
                {
                  //  Log.e("Error", "Failed to Login");
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
            return resultHolidayList;
        }

        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {
            super.onPostExecute(stringBuilder);
            dialog.dismiss();
            callback.onTaskComplete(stringBuilder.toString(),8);
        }

    }


