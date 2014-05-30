package ru.nsu.ccfit.hwr.logic.solver.tokenFactory;


import ru.nsu.ccfit.hwr.logic.solver.token.Token;
import ru.nsu.ccfit.hwr.logic.solver.token.BktOpen;

/**
 * Created by user_2 on 23.12.13.
 */
public class BktOpenFactory implements SymbolFactory {
    @Override
    public Token instanceToken(String name) {
        return new BktOpen();
    }
}
