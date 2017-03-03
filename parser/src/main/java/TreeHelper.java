import classes.CustomData;
import config.Constants;
import edu.stanford.nlp.trees.Tree;

import java.util.*;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TreeHelper {

    public static boolean isNP(Tree tree)
    {
        if (tree.value().toString().equals("NP")) {
            return true;
        }
        return false;
    }

    public static boolean isVP(Tree tree)
    {
        if (tree.value().toString().equals("VP")) {
            return true;
        }
        return false;
    }

    public static boolean isAdjective(String tag)
    {
        if(Arrays.asList(Constants.adjectiveSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isNoun(String tag)
    {
        if(Arrays.asList(Constants.nounSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isMainVerb(String tag)
    {
        if(Arrays.asList(Constants.mainVerbSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isHasJoinVerb(String tag)
    {
        if(Arrays.asList(Constants.joinVerbSet).contains(tag)) {
            return true;
        }
        return false;
    }


    public static boolean isHasPhrasesNP(Tree tree)
    {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {
            if(child.isPhrasal() && isNP(child)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHasPhrases(Tree tree)
    {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {
            if(child.isPhrasal()) {
                return true;
            }
        }
        return false;
    }

    public static void getNPTrees(Tree tree, List<Tree> nodesNP)
    {
        List<Tree> subtrees = tree.getChildrenAsList();
        for (Tree subtree: subtrees) {

            if(isNP(subtree) && !isHasPhrasesNP(subtree)) {
                //System.out.println("subtree: " + subtree);
                nodesNP.add(subtree);
            }
            getNPTrees(subtree, nodesNP);
        }
    }



    public static void getRelatedNP(Tree tree, List<CustomData> nodesNP, Tree root, Tree parentVP)
    {
        List<Tree> subtrees = tree.getChildrenAsList();
        boolean flag = false;
        for (Tree subtree: subtrees)
        {
            if(isNP(subtree) && !isHasPhrasesNP(subtree) && isHasConnection(subtree, root) == null)
            {
                //System.out.println("GET parent: " + parentNodeNP);
                System.out.println("GET subtree: " + subtree);
                Tree path = getPathToNode(subtree, parentVP, root);
                nodesNP.add(new CustomData(path, subtree));
            }
            else  if (isNP(subtree) && !isHasPhrasesNP(subtree) && isHasConnection(subtree, root) != null)
            {
                Tree path = getPathToNode(subtree, parentVP, root);
                nodesNP.add(new CustomData(path, subtree));
                //nodesNP.add(treeVP, subtree);
                flag = true;
            }
            if (flag == false)
            {
                getRelatedNP(subtree, nodesNP, root, parentVP);
            }


        }
    }



    public static Tree isHasConnection(Tree child, Tree root)
    {
        List<Tree> siblings = child.siblings(root);
        //Tree parent = child.parent(root);
        //List<Tree> siblings = parent.getChildrenAsList();

        for (Tree sibling : siblings) {

             if(isVP(sibling)) {
                 System.out.println("VP sibling: " + siblings);
                 return sibling;
             }
        }
        return null;
    }

    public static void investigateAllConnectedNodes(Tree tree, Tree nodeNP,  Tree root, List<CustomData> connectedNodesNP)
    {

        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {

            if(child.equals(nodeNP)) {
                System.out.println("======child: " + child);
                Tree treeVP = TreeHelper.isHasConnection(child, tree);
                if(treeVP != null) {
                    System.out.println("======treeVP: " + treeVP);
                    List<CustomData> connectedNodes = new ArrayList<CustomData>();
                    TreeHelper.getRelatedNP(treeVP, connectedNodes, root, treeVP);

                    connectedNodesNP.addAll(connectedNodes);
                }
            }
            investigateAllConnectedNodes(child, nodeNP, root, connectedNodesNP);
        }
    }


    public static Tree getPathToNode(Tree nodeNP,  Tree parentVP, Tree root)
    {
        List<Tree> paths = root.pathNodeToNode(parentVP, nodeNP);
        System.out.println("PATH: " + paths);
        return  getPathFromNodeToNode(paths, nodeNP);

    }

    public static Tree getPathFromNodeToNode(List<Tree> fullPath, Tree nodeNP) {


        Tree path = null;
        for (Tree subtree : fullPath) {
            if(isHasJoinVerb(subtree.value().toString()) && subtree.contains(nodeNP)) {
                System.out.println("SUBTREE: " + subtree);
                path = subtree;
            }

        }
//        if (path == null) {
//            for (Tree subtree : fullPath) {
//                if(subtree.contains(nodeNP)) {
//                    System.out.println("SUBTREE: " + subtree);
//                    path = subtree;
//                }
//
//            }
//        }
        return path;
    }
}
