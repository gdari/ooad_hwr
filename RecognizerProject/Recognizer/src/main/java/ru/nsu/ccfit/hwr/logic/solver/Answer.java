package ru.nsu.ccfit.hwr.logic.solver;

import java.io.Serializable;

/**
 * Created by user_2 on 21.12.13.
 */
public class Answer implements Serializable {
    String answer = null;

    Answer (String string) {
        answer = string;
    }

    public String toString() {
        return answer;
    }
}
