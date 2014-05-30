package ru.nsu.ccfit.hwr.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.nsu.ccfit.hwr.recognition.R;
import ru.nsu.ccfit.hwr.logic.solver.AbstractSolver;

public class SolvedStageActivity extends Activity {
    private AbstractSolver abstractSolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solved_stage_activity);
        // TODO :
        ExpressionView expressionView = (ExpressionView) findViewById(R.id.expressionView);
        Intent intent = getIntent();

        abstractSolver = (AbstractSolver)intent.getSerializableExtra(getResources().getString(R.string.to_solved_stage_intent));
        if (null == abstractSolver) {
            Log.e("ABSTRACT SOLVER IS NULL", "asdhasid");
        } else
        if (null == abstractSolver.get_answer()) {
            Log.e("Answer IS NULL", "asdhasid");
        } else
        expressionView.showString(abstractSolver.get_answer().toString());

        /*if (savedInstanceState == null) {
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.solved_stage, menu);
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

}
