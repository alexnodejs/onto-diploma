package graphs;

import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class NPNode {
    public int id;
    public Tree tree;

    public NPNode(int id, Tree tree) {
        this.id = id;
        this.tree = tree;
    }
}