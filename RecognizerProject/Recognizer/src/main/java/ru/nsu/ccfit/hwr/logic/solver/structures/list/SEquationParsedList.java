package ru.nsu.ccfit.hwr.logic.solver.structures.list;

import java.util.LinkedList;

import ru.nsu.ccfit.hwr.logic.solver.token.Token;
import ru.nsu.ccfit.hwr.logic.solver.structures.Structure;


/**
 * Created by user_2 on 22.12.13.
 */
public class SEquationParsedList implements Structure {
    private LinkedList<Token> parsedList;

    public SEquationParsedList() {
        this.parsedList = new LinkedList<Token>();
    }

    public LinkedList<Token> getParsedList() {
        return parsedList;
    }

    public void setParsedList(LinkedList<Token> parsedList) {
        this.parsedList = parsedList;
    }
}
