package ru.nsu.ccfit.hwr.logic.solver;


import java.io.Serializable;

import ru.nsu.ccfit.hwr.logic.solver.structures.Structure;

/**
 * Created by user_2 on 21.12.13.
 */
public interface AbstractSolver extends Serializable{
    public void   solve      (Structure structure);
    public Answer get_answer ();
}
