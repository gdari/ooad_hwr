package ru.nsu.ccfit.hwr.logic.solver.structures.tree;


import ru.nsu.ccfit.hwr.logic.solver.structures.Structure;

/**
 * Created by user_2 on 21.12.13.
 */
public interface ParsedTree extends Structure {
    public Node get_root();
    public void set_root(Node root);
}
