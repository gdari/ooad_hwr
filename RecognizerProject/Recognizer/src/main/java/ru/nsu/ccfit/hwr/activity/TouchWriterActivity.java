package ru.nsu.ccfit.hwr.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.nsu.ccfit.hwr.logic.recognition.data.PointsContainer;
import ru.nsu.ccfit.hwr.logic.recognition.data.Stroke;
import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.recognition.RecognitionTask;

public class TouchWriterActivity  extends Activity{
	private static final String errorResult = "Error";
    private final Drawing drawing = Drawing.getInstance();
    private DrawingView drawingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_writer_activity);

        drawingView = (DrawingView) findViewById(R.id.drawingView);
        /*if (savedInstanceState == null) {

        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.touch_writer, menu);
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

    private class RecognitionAsyncTask extends AsyncTask<List<PointsContainer>, Integer, SEquation>{
        private ProgressDialog progressDialog;
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            drawing.clear();
            drawingView.drawStrokes();
            progressDialog = new ProgressDialog(TouchWriterActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setIndeterminate(true);
        }
        @Override
        public SEquation doInBackground(List<PointsContainer> ... pointsContainers) {
            RecognitionTask task = RecognitionTask.getInstance(TouchWriterActivity.this);
            List<Stroke> result = task.segment(pointsContainers[0]);
           return task.recognize(result);
        }
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        public void onPostExecute(SEquation sEquation) {
            super.onPreExecute();
            progressDialog.dismiss();
        }
    }

    public void onClickOKButton(View view) {
        if (drawing.size() == 0 ) return;
        RecognitionAsyncTask task = new RecognitionAsyncTask();
        task.execute(drawing.getPoints());
		// clear
        // todo : new Intent
        SEquation result;
		try {
			result = task.get();
			if (null == result) {
				result = new SEquation(errorResult);
			}
            Log.i(TouchWriterActivity.class.getName(), result.toString());
        }
        catch (InterruptedException err) {
				result = new SEquation(errorResult);
        }
        catch (ExecutionException err) {
                result = new SEquation(errorResult);
        }
        Intent intent = new Intent(this, ParsedStageActivity.class);
        intent.putExtra(getResources().getString(R.string.to_parsed_stage_intent) , result);
        startActivity(intent);
    }

    public void onClickCancelButton(View view) {
		if (drawing.cancel()) {
			drawingView.drawStrokes();
		}
    }

    public void onClickClear(View view) {
        drawing.clear();
        drawingView.startNew();
    }
}
