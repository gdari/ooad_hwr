package ru.nsu.ccfit.hwr.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.nsu.ccfit.hwr.recognition.R;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        /*if (savedInstanceState == null) {
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickDraw(View view) {
        Intent openDrawActivity = new Intent(this, TouchWriterActivity.class);
        startActivity(openDrawActivity);
    }

    public void onClickWrite(View view) {
        Intent openTextEnterActivity = new Intent(this, EquationEnterActivity.class);
        startActivity(openTextEnterActivity);
    }
}
