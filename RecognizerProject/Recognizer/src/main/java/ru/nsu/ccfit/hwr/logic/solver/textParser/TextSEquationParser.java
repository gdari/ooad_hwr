package ru.nsu.ccfit.hwr.logic.solver.textParser;

import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.solver.structures.Structure;

/**
 * Created by user_2 on 21.12.13.
 */
public interface TextSEquationParser {
    Structure getStructure();
    void parse(SEquation sEquation);
}



