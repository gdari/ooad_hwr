package ru.nsu.ccfit.hwr.logic.solver.tokenFactory;


import ru.nsu.ccfit.hwr.logic.solver.token.Token;

/**
 * Created by user_2 on 21.12.13.
 */
public interface AbstractTokenFactory {
    public Token instanceToken (String name);
}
