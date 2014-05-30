package ru.nsu.ccfit.hwr.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.solver.AbstractSolver;
import ru.nsu.ccfit.hwr.logic.solver.SolveController;


public class EquationEnterActivity extends Activity {
    private EditText editText;
    private ExpressionView expressionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eq_enter_activity);
        editText = (EditText) findViewById(R.id.equationEditText);
        expressionView = (ExpressionView) findViewById(R.id.expressionView);
        /*if (savedInstanceState == null) {

        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.equation_enter, menu);
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

    public void onClickShowButton(View view) {
        if (editText.getText() == null) return;
        expressionView.clear();
        expressionView.showString(editText.getText().toString());
    }

    public void onClickSolveButton(View view) {
        if (editText.getText() == null || "".equals(editText.getText().toString())) return;
        Intent intent = new Intent(this, SolvedStageActivity.class);
        // TODO :
        SolveAsyncTask solveAsyncTask = new SolveAsyncTask();
        solveAsyncTask.execute(new SEquation(editText.getText().toString()));

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
            progressDialog = new ProgressDialog(EquationEnterActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setIndeterminate(true);
        }

        @Override
        public AbstractSolver doInBackground(SEquation ... sEquations) {
            SolveController solveController = SolveController.getInstance();
           // Log.i("SEQUATION", sEquations[0].toString());
            solveController.chooseMode(EquationEnterActivity.this, sEquations[0]);
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
