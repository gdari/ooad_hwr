package ru.nsu.ccfit.hwr.logic.solver.structures.tree;

/**
 * Created by user_2 on 21.12.13.
 */
public class SEquationParsedTree implements ParsedTree {

    private Node root;

    public SEquationParsedTree() {
        this.root = null;
    }

    @Override
    public Node get_root() {
        return root;
    }

    @Override
    public void set_root(Node root_) {
        root = root_;
    }
}
