package graphs;

import edu.stanford.nlp.trees.Tree;


/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class NPEdge {
    public int id;
    public  int parentNodeId;
    public  int childNodeId;
    public Tree path;

    public  NPEdge(int id, Tree path, int parentNodeId, int childNodeId) {
        this.path = path;
        this.id = id;
        this.parentNodeId = parentNodeId;
        this.childNodeId = childNodeId;
    }
}
