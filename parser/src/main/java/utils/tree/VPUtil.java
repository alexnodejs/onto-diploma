package utils.tree;

import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/9/17.
 */
public class VPUtil extends BaseTreeUtil {

    public static List<Tree> getIncludedVP(Tree parentVP)
    {
        List<Tree> vps = new ArrayList<Tree>();
        List<Tree> children = parentVP.getChildrenAsList();
        for(Tree child : children) {
            if(isVP(child)) {
                vps.add(child);
            }
        }
        return vps;
    }

    public static Tree getPathToNode(Tree nodeNP,  Tree parentVP, Tree root)
    {
        List<Tree> paths = root.pathNodeToNode(parentVP, nodeNP);
        System.out.println("PATH: " + paths);

        Tree pathDescriptor = getDescriptor(parentVP);
        Tree path = getPathFromNodeToNode(paths, nodeNP);
        System.out.println("pathDescriptor: " + pathDescriptor);
        System.out.println("path: " + path);

        if(path == null) {
            path = pathDescriptor;
        } else if (pathDescriptor != null) {
            Tree complexTree = path.deepCopy();
            complexTree.insertDtr(pathDescriptor, 0);
            return complexTree;
        }

        return path;
    }

    private static Tree getPathFromNodeToNode(List<Tree> fullPath, Tree nodeNP)
    {
        Tree path = null;
        for (Tree subtree : fullPath) {
            if(isHasJoinVerb(subtree.value().toString()) && subtree.contains(nodeNP)) {
                //System.out.println("PATH FROM NODE TO NODE: " + subtree);
                return subtree;
            }
        }
        return path;
    }

    private static Tree getDescriptor(Tree parentVP)
    {
        Tree tree = null;
        Tree copyParentVP = parentVP.deepCopy();
        if (isVP(copyParentVP.firstChild())) {
            copyParentVP = copyParentVP.firstChild();
        }

        List<Tree> paths = copyParentVP.getChildrenAsList();
        for (Tree child : paths) {
            if (isVerb(child.value().toString())) {
                if (tree == null) {
                    tree = child;
                } else {
                    tree.insertDtr(child, 0);
                }
            }
        }
        return tree;
    }
}
