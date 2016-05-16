package com.realizer.rapido;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class SurveryDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_question_layout);
    }

    // set next question here
    public void SetNextQuestion(View V)
    {
        Toast.makeText(getApplicationContext(),"Set Next ", Toast.LENGTH_LONG).show();
    }

    // save as draft
    public void  SaveDraft(View V)
    {
        Toast.makeText(getApplicationContext(),"Saved Draft ", Toast.LENGTH_LONG).show();
    }

    // submit survey
    public void Submit(View V)
    {
        Toast.makeText(getApplicationContext(),"Survey Submitted ", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
}
