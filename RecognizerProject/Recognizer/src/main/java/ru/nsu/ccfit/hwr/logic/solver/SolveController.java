package ru.nsu.ccfit.hwr.logic.solver;

import android.content.Context;

import ru.nsu.ccfit.hwr.logic.solver.textParser.TextSEquationParser;
import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.solver.textParser.ReverseNotationParser;

/**
 * Created by user_2 on 23.12.13.
 */
public class SolveController {
    static private SolveController instance;
    static public SolveController getInstance() {
        if (null == instance)
            instance = new SolveController();
        return instance;
    }
    private SolveController() {}
    private AbstractSolver      solver              = null;
    private TextSEquationParser textSEquationParser = null;
    private SEquation           sEquation           = null;

    public void chooseMode(Context context, SEquation sEquation) {
        this.sEquation = sEquation;
        solver = new Calculator();
        textSEquationParser = new ReverseNotationParser(context);
    }

    public void start() {
        textSEquationParser.parse(sEquation);
        solver.solve(textSEquationParser.getStructure());
    }

    public AbstractSolver getSolver() {
        return solver;
    }

}
