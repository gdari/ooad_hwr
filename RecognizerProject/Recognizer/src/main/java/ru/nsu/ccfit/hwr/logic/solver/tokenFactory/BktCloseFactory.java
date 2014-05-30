package ru.nsu.ccfit.hwr.logic.solver.tokenFactory;


import ru.nsu.ccfit.hwr.logic.solver.token.Token;
import ru.nsu.ccfit.hwr.logic.solver.token.BktClose;

/**
 * Created by user_2 on 23.12.13.
 */
public class BktCloseFactory implements SymbolFactory {
    @Override
    public Token instanceToken(String name) {
        return new BktClose();
    }
}
