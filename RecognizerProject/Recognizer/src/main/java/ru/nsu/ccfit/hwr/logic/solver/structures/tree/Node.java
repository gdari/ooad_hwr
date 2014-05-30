package ru.nsu.ccfit.hwr.logic.solver.structures.tree;

import java.util.ArrayList;

import ru.nsu.ccfit.hwr.logic.solver.token.Token;


/**
 * Created by user_2 on 21.12.13.
 */
public class Node {
    private Node             parent;
    private ArrayList<Node>  child;
    private Token token;

    public Node() {
        parent = null;
        child  = null;
        token  = null;
    }

    public Node             getParent   () {
        return parent;
    }

    public ArrayList<Node>  getChild    () {
        return child;
    }

    public Token            getToken    () {
        return token;
    }

    public void             setParent   (Node parent) {
        this.parent = parent;
    }

    public void             setChild    (ArrayList<Node> child) {
        this.child = child;
    }

    public void             setToken    (Token token) {
        this.token = token;
    }
}
