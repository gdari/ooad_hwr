package ru.nsu.ccfit.hwr.logic.solver;

import java.util.LinkedList;
import java.util.Stack;

import ru.nsu.ccfit.hwr.logic.solver.structures.Structure;
import ru.nsu.ccfit.hwr.logic.solver.structures.list.SEquationParsedList;
import ru.nsu.ccfit.hwr.logic.solver.token.Token;


/**
 * Created by user_2 on 21.12.13.
 */
public class Calculator implements AbstractSolver {
    private Answer answer;

    @Override
    public void solve(Structure structure) {
        SEquationParsedList equationParsedList = (SEquationParsedList) structure;
        LinkedList<Token>   tokenLinkedList    = equationParsedList.getParsedList();
        Stack<Float>        floatStack         = new Stack<Float>();

        for (Token token : tokenLinkedList) {
            if (const_priority == token.get_priority()) {
                floatStack.push(token.perform(floatStack));
            }
            else {
                token.perform(floatStack);
            }
        }

        answer = new Answer(Float.toString(floatStack.pop()));
    }

    @Override
    public Answer get_answer() {
        return answer;
    }

    private static final int const_priority = 0;
}
