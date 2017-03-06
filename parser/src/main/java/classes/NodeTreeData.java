package classes;

import edu.stanford.nlp.trees.Tree;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class NodeTreeData {
    public Tree node;
    public Tree path;

    public NodeTreeData(Tree path, Tree node) {
        this.path = path;
        this.node = node;
    }
}
