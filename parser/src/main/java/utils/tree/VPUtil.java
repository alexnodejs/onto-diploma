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
        Tree joinedVPTree = joinedVPTree(parentVP);

        Tree path = getPathFromNodeToNode(paths, nodeNP);
        System.out.println("joinedVPTree: " + joinedVPTree);
        System.out.println("pathDescriptor: " + pathDescriptor);
        System.out.println("path: " + path);


       if (pathDescriptor != null) {
           if (path != null) {
               Tree complexTree = path.deepCopy();
               complexTree.addChild(pathDescriptor);
               path = complexTree;
           } else {
               path = pathDescriptor;
           }
        }

        if (joinedVPTree != null) {
            if (path != null) {
                Tree complexTree = path.deepCopy();
                complexTree.addChild(joinedVPTree);
                path = complexTree;
            } else {
                path = joinedVPTree;
            }
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



    private static boolean hasConjVP(Tree parentVP) {

        Tree copyParentVP = parentVP.deepCopy();
        List<Tree> paths = copyParentVP.getChildrenAsList();
        for (Tree child : paths) {
            System.out.println("hasConjVP: " + child);
            if(BaseTreeUtil.isVP(child)) {
                return true;
            }
//            if(BaseTreeUtil.isConj(child.value().toString())) {
//                System.out.println("hasJoinedVP: " + child);
//                return true;
//            }
        }
        return false;
    }

    private static Tree joinedVPTree(Tree parentVP) {

        Tree tree = null;
        if(!hasConjVP(parentVP)){
            return tree;
        }

        Tree copyParentVP = parentVP.deepCopy();


        List<Tree> paths = copyParentVP.getChildrenAsList();
        for (Tree childPath : paths) {

            if (isVP(childPath)) {
                System.out.println("isVerb buildConjVP: " + childPath);
                if (tree == null) {
                    tree = childPath;
                } else {
                    tree.addChild(childPath);
                }
//                List<Tree> childrenAsList = childPath.getChildrenAsList();
//                for (Tree child : childrenAsList) {
//                    if (isVerb(child.value().toString())) {
//                        System.out.println("isVerb buildConjVP: " + child);
//                        if (tree == null) {
//                            tree = child;
//                        } else {
//                            tree.addChild(child);
//                        }
//                    }
//                }
            }
            if(isConj(childPath.value().toString())) {
                if (tree == null) {
                    tree = childPath;
                } else {
                    tree.addChild(childPath);
                }
            }
        }
        return tree;
    }

    private static Tree getDescriptor(Tree parentVP)
    {
        Tree tree = null;
        Tree copyParentVP = parentVP.deepCopy();

        if (isVP(copyParentVP.firstChild())) {
            return tree;
        }


        List<Tree> paths = copyParentVP.getChildrenAsList();
        for (Tree child : paths) {
            if (isVerb(child.value().toString())) {
                if (tree == null) {
                    tree = child;
                } else {
                    tree.addChild(child);
                }
            }
            // For negative questions
            if (isAdjective(child.value().toString())) {
                if (tree == null) {
                    tree = child;
                } else {
                    tree.addChild(child);
                }
            }
        }
        return tree;
    }

//    private static Tree getDescriptor(Tree parentVP)
//    {
//        Tree tree = null;
//        Tree copyParentVP = parentVP.deepCopy();
//        if (isVP(copyParentVP.firstChild())) {
//            copyParentVP = copyParentVP.firstChild();
//        }
//
//        List<Tree> paths = copyParentVP.getChildrenAsList();
//        for (Tree child : paths) {
//            System.out.println("child: " + child);
//            if (isVerb(child.value().toString())) {
//                if (tree == null) {
//                    tree = child;
//                } else {
//                    //tree.insertDtr(child, 0);
//                    tree.addChild(child);
//                }
//            }
//            // For negative questions
//            if (isAdjective(child.value().toString())) {
//                if (tree == null) {
//                    tree = child;
//                } else {
//                    tree.addChild(child);
//                }
//            }
//        }
//        return tree;
//    }
}
