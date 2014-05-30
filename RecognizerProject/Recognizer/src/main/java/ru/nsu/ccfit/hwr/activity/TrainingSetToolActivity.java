package ru.nsu.ccfit.hwr.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.nsu.ccfit.hwr.logic.recognition.FeatureVector;
import ru.nsu.ccfit.hwr.logic.recognition.TrainingSet;
import ru.nsu.ccfit.hwr.logic.recognition.TrainingSetDBWriter;
import ru.nsu.ccfit.hwr.logic.recognition.data.PointsContainer;
import ru.nsu.ccfit.hwr.logic.recognition.data.Stroke;
import ru.nsu.ccfit.hwr.logic.recognition.data.StrokesGroup;
import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.recognition.kNNClassifier.KNNClassifier;

public class TrainingSetToolActivity extends Activity {
    private DrawingView drawingView = null;
    private EditText editText = null;	
    private final Drawing drawing = Drawing.getInstance();
    private final TrainingSet trainingSet = new TrainingSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_set_tool_activiity);
        drawingView = (DrawingView) findViewById(R.id.drawingView);
        editText = (EditText) findViewById(R.id.label);
		
        /*if (savedInstanceState == null) {
			// TODO :
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.training_set_tool, menu);
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

    public void onClickOKButton(View view) {
        if (null == editText.getText() || "".equals(editText.getText().toString()) ) {
            return;// TODO:
        }
        FeatureExtraction featureExtraction = new FeatureExtraction();
        featureExtraction.execute(drawing.getPoints());

        try {
            featureExtraction.get();
        }
        catch (ExecutionException err) {
            err.printStackTrace();
        }
        catch (InterruptedException err) {
	        err.printStackTrace();
        }
    }

    private class FeatureExtraction extends AsyncTask <List<PointsContainer>, Integer, FeatureVector> {
        private ProgressDialog progressDialog;
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(TrainingSetToolActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressDialog.setMax(100);
            //progressDialog.setProgress(0);
            progressDialog.show();
            progressDialog.setIndeterminate(true);
        }

        protected void onPostExecute(FeatureVector f) {
            super.onPostExecute(f);
            drawing.clear();
            drawingView.startNew();
            //drawingView.drawStrokes();
            if (editText.getText() != null)
                editText.getText().clear();
            //progressDialog.setProgress(progressDialog.getMax());
            progressDialog.dismiss();
        }

        public FeatureVector doInBackground(List<PointsContainer> ... lists){
            int K = TrainingSetToolActivity.this.getResources().getInteger(R.integer.k_neighbours);
            int patternSize = TrainingSetToolActivity.this.getResources().getInteger(R.integer.pattern_size);
            int N = getResources().getInteger(R.integer.points_count);
			
            KNNClassifier knnClassifier = new KNNClassifier(K, patternSize, N );
            //setProgress(1);
            Stroke stroke = new Stroke(lists[0].get(0));
            StrokesGroup strokesGroup = new StrokesGroup(stroke);
            for (int i = 1; i < lists[0].size(); ++i) {
                Stroke s = new Stroke(lists[0].get(i));
                //s = knnClassifier.prepare(s);
                strokesGroup.addStroke(s);
                //setProgress((int)(i* 40.0f/lists[0].size()));
            }
            FeatureVector f = knnClassifier.extract(strokesGroup);
            //setProgress(90);
            if (editText.getText() != null)
                trainingSet.addPattern(editText.getText().toString(), f );
            //setProgress(95);
            return  f;
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            //progressDialog.setProgress(progress[0]);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TrainingSetDBWriter trainingSetDBWriter = new TrainingSetDBWriter(this);
        try {
            trainingSetDBWriter.writeTrainingSet(trainingSet);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    public void onClickCancelButton(View view) {
        if (drawing.cancel()) {
            drawingView.drawStrokes();
        }
    }
}
