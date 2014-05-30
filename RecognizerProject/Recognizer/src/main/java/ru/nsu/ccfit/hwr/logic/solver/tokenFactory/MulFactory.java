package ru.nsu.ccfit.hwr.logic.solver.tokenFactory;


import ru.nsu.ccfit.hwr.logic.solver.token.Multiplication;
import ru.nsu.ccfit.hwr.logic.solver.token.Token;

/**
 * Created by user_2 on 23.12.13.
 */
public class MulFactory implements FunctionFactory {

    @Override
    public Token instanceToken(String name) {
        return new Multiplication();
    }
}
