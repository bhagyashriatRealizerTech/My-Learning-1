package com.realizer.rapido;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Suvarna.Ghoman on 14-09-2015.
 */
public class SurveyQuestionSaveAnswerPost extends AsyncTask<Void, Void,StringBuilder>
{

    ProgressDialog diaglog;
    StringBuilder builder;
    String empId, surveyCode, answer,qstion,isReview,options;
    Integer srNo;
    Context myContext;
    String FLAG;

    public SurveyQuestionSaveAnswerPost(String _empId, String _surveyCode, Integer _srNo, String qstion, String _answer, String isReview, String options, String FLAG,Context _context)
    {
        this.empId = _empId;
        this.surveyCode=_surveyCode;
        this.srNo=_srNo;
        this.myContext = _context;
        this.answer =_answer;
        this.qstion = qstion;
        this.isReview = isReview;
        this.options = options;
        this.FLAG = FLAG;
    }

    @Override
    protected StringBuilder doInBackground(Void... params1) {
        builder = new StringBuilder();

        String HostUrl="http://104.217.254.180/RestWCF/svcEmp.svc/SaveSurveyAnswer/"+empId+"/"+surveyCode+"/"+String.valueOf(srNo)+"/"+qstion+"/"+answer+"/"+isReview+"/"+options+"/"+FLAG;

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(HostUrl);
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
                    builder.append(line);
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
        return builder;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // diaglog = ProgressDialog.show(myContext,"Addition", "Adding Login Info !");
    }

    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        super.onPostExecute(stringBuilder);
       // diaglog.dismiss();
    }
}
