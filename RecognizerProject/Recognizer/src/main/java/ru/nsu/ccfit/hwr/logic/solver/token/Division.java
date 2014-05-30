package ru.nsu.ccfit.hwr.logic.solver.token;
import java.util.Stack;

/**
 * Created by user_2 on 23.12.13.
 */
public class Division implements Function{
    private static final int priority = 3;

    @Override
    public float perform(Stack<Float> floatStack) {
        float farg, sarg, res;

        sarg = floatStack.pop();
        farg = floatStack.pop();
        if (sarg != 0)
            res  = farg / sarg;
        else
            res = 0.f;
        floatStack.push(res);

        return res;
    }

    @Override
    public int get_priority() {
        return priority;
    }
}
