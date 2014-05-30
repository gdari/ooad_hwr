package ru.nsu.ccfit.hwr.logic.solver.token;

import java.util.Stack;

/**
 * Created by user_2 on 21.12.13.
 */
public class Constant implements Symbol {

    private float value    = 0;
    private static final int  priority = 0;

    public Constant(float val) {
        value = val;
    }

    @Override
    public float perform(Stack<Float> floatStack) {
        return value;
    }

    @Override
    public int get_priority() {
        return priority;
    }
}
