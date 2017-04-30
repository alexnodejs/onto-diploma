package utils.tree;

import classes.NodeTreeData;
import edu.stanford.nlp.trees.Tree;

import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/9/17.
 */
public class NPUtil extends BaseTreeUtil {

    public static void getNPwithPath(Tree tree, List<NodeTreeData> nodesNP, Tree root, Tree parentVP)
    {
        List<Tree> subtrees = tree.getChildrenAsList();
        boolean flag = false;
        for (Tree subtree: subtrees)
        {
            if(isNP(subtree) && !isHasPhrasesNP(subtree) && isHasRelation(subtree, root) == null)
            {
                Tree path = VPUtil.getPathToNode(subtree, parentVP, root);
                nodesNP.add(new NodeTreeData(path, subtree));
            }
            else if (isNP(subtree) && !isHasPhrasesNP(subtree) && isHasRelation(subtree, root) != null)
            {
                Tree path = VPUtil.getPathToNode(subtree, parentVP, root);
                nodesNP.add(new NodeTreeData(path, subtree));
                flag = true;
            }
            if (flag == false)
            {
                getNPwithPath(subtree, nodesNP, root, parentVP);
            }
        }
    }
}
