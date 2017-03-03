package graphs;

import edu.stanford.nlp.trees.Tree;


/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class NPEdge {
    public int id;
    public  int padentNodeId;
    public  int childNodeId;
    public Tree path;

    public  NPEdge(int id, Tree path, int padentNodeId, int childNodeId) {
        this.path = path;
        this.id = id;
        this.padentNodeId = padentNodeId;
        this.childNodeId = childNodeId;
    }
}
