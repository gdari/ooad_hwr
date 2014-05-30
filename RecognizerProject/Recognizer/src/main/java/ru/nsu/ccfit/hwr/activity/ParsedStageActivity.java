package ru.nsu.ccfit.hwr.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.solver.AbstractSolver;
import ru.nsu.ccfit.hwr.logic.solver.SolveController;

public class ParsedStageActivity extends Activity {
    private ExpressionView expressionView;
    private SEquation sEquation;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parsed_stage_activity);
        expressionView = (ExpressionView)  findViewById(R.id.parsedExpressionView);
        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        sEquation = (SEquation) intent.getSerializableExtra(getResources().getString(R.string.to_parsed_stage_intent));
        if (sEquation == null) {
            //TODO:
            return;
        }
        expressionView.showString(sEquation.toString());
        textView.setText(sEquation.toString());
        /*if (savedInstanceState == null) {
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parsed_stage, menu);
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

    public void onClickSolveButton(View view) {
        Intent intent = new Intent(this, SolvedStageActivity.class);
        // TODO :
        SolveAsyncTask solveAsyncTask = new SolveAsyncTask();
        solveAsyncTask.execute(sEquation);

        AbstractSolver abstractSolver = null;
        try {
           abstractSolver = solveAsyncTask.get();
        } catch (ExecutionException err) {

        } catch (InterruptedException err) {

        }

        // TODO:
        intent.putExtra(getResources().getString(R.string.to_solved_stage_intent), abstractSolver);
        expressionView.clear();
        startActivity(intent);
    }
    // todo:
    private class SolveAsyncTask extends AsyncTask<SEquation, Integer, AbstractSolver> {
        private ProgressDialog progressDialog;
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ParsedStageActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setIndeterminate(true);
        }

        @Override
        public AbstractSolver doInBackground(SEquation ... sEquations) {
            SolveController solveController = SolveController.getInstance();
            solveController.chooseMode(ParsedStageActivity.this, sEquations[0]);
            solveController.start();
            return solveController.getSolver();
        }

        @Override
        public void onPostExecute(AbstractSolver abstractSolver) {
            super.onPreExecute();
            progressDialog.dismiss();
        }
    }

}
