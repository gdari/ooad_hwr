package ru.nsu.ccfit.hwr.logic.solver.token;

import java.util.Stack;

/**
 * Created by user_2 on 23.12.13.
 */
public class Multiplication implements Function{
    private static final int priority = 3;

    @Override
    public float perform(Stack<Float> floatStack) {
        float farg, sarg, res;

        sarg = floatStack.pop();
        farg = floatStack.pop();
        res  = farg * sarg;
        floatStack.push(res);

        return res;
    }

    @Override
    public int get_priority() {
        return priority;
    }
}
