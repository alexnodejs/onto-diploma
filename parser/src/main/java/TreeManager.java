import classes.NodeTreeData;
import edu.stanford.nlp.trees.Tree;
import utils.tree.BaseTreeUtil;

import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/9/17.
 */
public class TreeManager {

    private static TreeManager instance;
    public static synchronized TreeManager getInstance() {
        if (instance == null) {
            instance = new TreeManager();
        }
        return instance;
    }

    public void getNPTrees(Tree tree, List<Tree> nodesNP) {
        BaseTreeUtil.getNPTrees(tree, nodesNP);
    }

    public void getRelatedNP(Tree tree, Tree nodeNP, Tree root, List<NodeTreeData> connectedNodesNP) {
        BaseTreeUtil.getRelatedNP(tree, nodeNP, root,connectedNodesNP);
    }
}
