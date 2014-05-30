package ru.nsu.ccfit.hwr.logic.solver.textParser;

import android.content.Context;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import ru.nsu.ccfit.hwr.logic.SEquation;
import ru.nsu.ccfit.hwr.logic.solver.token.StackEnd;
import ru.nsu.ccfit.hwr.logic.solver.token.Token;
import ru.nsu.ccfit.hwr.logic.solver.structures.Structure;
import ru.nsu.ccfit.hwr.logic.solver.structures.list.SEquationParsedList;

/**
 * Created by user_2 on 22.12.13.
 */
public class ReverseNotationParser implements TextSEquationParser {
    private final SEquationParsedList equationParsedList = new SEquationParsedList();
    private Context context;
    private LinkedList<String> loadStringList (SEquation sEquation) {
        LinkedList<String> linkedList  = new LinkedList<String>();
        String             string      = sEquation.toString();
        String []          stringParts = string.split(" ");

        Collections.addAll(linkedList, stringParts);

        return linkedList;
    }

    public ReverseNotationParser(Context context) {
        this.context = context;
    }

    @Override
    public Structure getStructure() {
        return equationParsedList;
    }

    @Override
    public void parse(SEquation sEquation) {
        LinkedList<String> stringLinkedList = loadStringList(sEquation);
        LinkedList<Token>  tokenLinkedList  = new LinkedList<Token>();
        Stack<Token>       tokenStack       = new Stack<Token>();
        Dictionary         dictionary       = new Dictionary();
        Token              cur_token;

        dictionary.load_dictionary(context, "dictionary_config.properties");
        cur_token = new StackEnd();
        tokenStack.push(cur_token);

        for (String cur_str : stringLinkedList) {
            cur_token = dictionary.get_token(cur_str);

            //если константа, то в выход. строку
            if (const_priority == cur_token.get_priority()) {
                tokenLinkedList.add(cur_token);
            }
            else {
                //если закрывающая скобка, то выталкивать из стека пока не найдем открывающую
                if (bkt_close_ptiority == cur_token.get_priority()) {
                    while (bkt_open_ptiority != tokenStack.peek().get_priority()) {
                        tokenLinkedList.add(tokenStack.pop());
                    }
                    tokenStack.pop();
                }
                else {
                    if (bkt_open_ptiority == cur_token.get_priority()) {
                        tokenStack.push(cur_token);
                    }
                    else {
                        if (!tokenStack.isEmpty()) {
                            while (tokenStack.peek().get_priority() >= cur_token.get_priority()) {
                                tokenLinkedList.add(tokenStack.pop());
                            }
                            tokenStack.push(cur_token);
                        }
                    }
                }
            }
        }
        while (tokenStack.peek().get_priority() != stack_end) {
            tokenLinkedList.add(tokenStack.pop());
        }

        equationParsedList.setParsedList(tokenLinkedList);
    }

    private static final int stack_end = -1;
    private static final int const_priority = 0;
    private static final int bkt_close_ptiority   = -2;
    private static final int bkt_open_ptiority   = -3;
}
