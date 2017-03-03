package graphs;

import edu.stanford.nlp.trees.Tree;


/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class NPEdge {
    public int index;
    public Tree path;

    public  NPEdge(int index, Tree path) {
        this.path = path;
        this.index = index;
    }
}
