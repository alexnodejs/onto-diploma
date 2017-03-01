import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class TreeHelper {

    public static boolean isNP(Tree tree) {
        if (tree.value().toString().equals("NP")) {
            return true;
        }
        return false;
    }

    public static boolean isVP(Tree tree) {
        if (tree.value().toString().equals("VP")) {
            return true;
        }
        return false;
    }

    public static boolean isHasPhrasesNP(Tree tree) {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {
            if(child.isPhrasal() && isNP(child)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHasPhrases(Tree tree) {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {
            if(child.isPhrasal()) {
                return true;
            }
        }
        return false;
    }

    public static void getNPTrees(Tree tree, List<Tree> nodesNP) {
        List<Tree> subtrees = tree.getChildrenAsList();
        for (Tree subtree: subtrees) {

            if(isNP(subtree) && !isHasPhrasesNP(subtree)) {
                //System.out.println("subtree: " + subtree);
                nodesNP.add(subtree);
            }
            getNPTrees(subtree, nodesNP);
        }
    }



    public static void getNPTreesWithoutVP(Tree tree, List<Tree> nodesNP, Tree root, Tree parentNodeNP) {
        List<Tree> subtrees = tree.getChildrenAsList();
        boolean flag = false;

        for (Tree subtree: subtrees) {

            //System.out.println("======subtree subtree: " + subtrees);
            if(isNP(subtree) && !isHasPhrasesNP(subtree) && isHasConnection(subtree, root) == null) {
                System.out.println("GET subtree: " + subtree);
                //List<Tree> path = root.pathNodeToNode(parentNodeNP, subtree);
                //System.out.println("path: " + path);
                nodesNP.add(subtree);
            }
            else  if (isNP(subtree) && !isHasPhrasesNP(subtree) && isHasConnection(subtree, root) != null) {
                System.out.println("Additional GET subtree: " + subtree);
                nodesNP.add(subtree);
                flag = true;
            }
            if (flag == false) {
                getNPTreesWithoutVP(subtree, nodesNP, root, parentNodeNP);
            }
        }
    }

    public static Tree isHasConnection(Tree child, Tree root) {
        //List<Tree> siblings = child.siblings(root);
        Tree parent = child.parent(root);
        List<Tree> siblings = parent.getChildrenAsList();

        for (Tree sibling : siblings) {

             if(isVP(sibling)) {
                 System.out.println("VP sibling: " + siblings);
                 return sibling;
             }
        }
        return null;
    }

    public static void investigateAllConnectedNodes(Tree tree, Tree nodeNP,  Tree root, List<Tree> connectedNodesNP) {

        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {

            if(child.equals(nodeNP)) {
                System.out.println("======child: " + child);
                Tree treeVP = TreeHelper.isHasConnection(child, tree);
                if(treeVP != null) {
                    System.out.println("======treeVP: " + treeVP);
                    List<Tree> connectedNodes = new ArrayList<Tree>();
                    TreeHelper.getNPTreesWithoutVP(treeVP, connectedNodes, root, nodeNP);
                    //System.out.println("Parent Node: " + child);
                    //System.out.println("connected nodes: " + connectedNodes); //TODO: return node: path
                    connectedNodesNP.addAll(connectedNodes);
                }
            }
            investigateAllConnectedNodes(child, nodeNP, root, connectedNodesNP);
        }
    }

    public static void connectNodeWithChilds(Tree parentNode, List<Tree> childs) {

    }



}
