package com.realizer.rapido;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Suvarna.Ghoman on 03-09-2015.
 */
public class ApproveTimeSheetRejectedAsyncTaskPost  extends AsyncTask<Void, Void,StringBuilder>
{

    ProgressDialog dialog;
    StringBuilder builder;
    String empId, weekDate, totalHrs, reasonForRejection;
    Context myContext;

    public ApproveTimeSheetRejectedAsyncTaskPost(String _empIdForTimeSheet, String _weekDate, String _totalHrs, String _reasonForRejection, Context _context)
    {
        this.empId = _empIdForTimeSheet;
        this.weekDate=_weekDate;
        this.totalHrs = _totalHrs;
        this.reasonForRejection=_reasonForRejection;
        this.myContext = _context;
    }

    @Override
    protected StringBuilder doInBackground(Void... params1) {
        builder = new StringBuilder();

        String HostUrl="http://192.168.1.101/webapi/api/values/Post";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(HostUrl);
        try
        {
            List<NameValuePair> params = new LinkedList<NameValuePair>();

            params.add(new BasicNameValuePair("EmpId",String.valueOf(empId)));
            params.add(new BasicNameValuePair("WeekDate",String.valueOf(weekDate)));
            params.add(new BasicNameValuePair("TotalHrs",String.valueOf(totalHrs)));
            params.add(new BasicNameValuePair("ReasonOfRejection",String.valueOf(reasonForRejection)));

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Accept", "application/json");

            HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String resultResponse;
            resultResponse=httpClient.execute(httpPost,handler);

            builder.append(resultResponse);


        }
        catch (ClientProtocolException e)
        {
            String src = "ClientProtocolException ";
            e.printStackTrace();
            Log.e(src, "ClientProtocolException in callWebService(). " + e.getMessage());
            builder.append("ClientProtocolException in callWebService(). " + e.getMessage());
        }
        catch (IOException e)
        {
            String src = "IOException";
            e.printStackTrace();
            Log.e(src, "IOException in callWebService(). " + e.getMessage());
            builder.append("IOException in callWebService(). " + e.getMessage());
        }

        return builder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(myContext,"Addition", "Adding Login Info !");
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
        dialog.dismiss();
    }
}
