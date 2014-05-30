package ru.nsu.ccfit.hwr.logic.solver.token;
import java.util.Stack;

/**
 * Created by user_2 on 23.12.13.
 */
public class StackEnd implements Nonterminal {
    private static final int priopiry = -1;

    @Override
    public float perform(Stack<Float> floatStack) {
        return 0;
    }

    @Override
    public int get_priority() {
        return priopiry;
    }
}
