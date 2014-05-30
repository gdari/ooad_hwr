package ru.nsu.ccfit.hwr.logic.solver.tokenFactory;


import ru.nsu.ccfit.hwr.logic.solver.token.Sum;
import ru.nsu.ccfit.hwr.logic.solver.token.Token;

/**
 * Created by user_2 on 21.12.13.
 */
public class SumFactory implements FunctionFactory {

    @Override
    public Token instanceToken(String name) {
        return new Sum();
    }
}
