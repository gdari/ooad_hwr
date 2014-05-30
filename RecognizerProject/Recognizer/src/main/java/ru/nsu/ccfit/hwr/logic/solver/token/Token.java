package ru.nsu.ccfit.hwr.logic.solver.token;
import java.util.Stack;

/**
 * Created by user_2 on 21.12.13.
 */
public interface Token {
    public float perform (Stack<Float> floatStack);
    public int   get_priority();
}
