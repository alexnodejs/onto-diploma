import classes.NodeTreeData;
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

    public static boolean isVerb(String tag)
    {
        if(Arrays.asList(Constants.verbSet).contains(tag)) {
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



    public static void getRelatedNP(Tree tree, List<NodeTreeData> nodesNP, Tree root, Tree parentVP)
    {
        List<Tree> subtrees = tree.getChildrenAsList();
        boolean flag = false;
        for (Tree subtree: subtrees)
        {
            if(isNP(subtree) && !isHasPhrasesNP(subtree) && isHasConnection(subtree, root) == null)
            {
                System.out.println("GET subtree: " + subtree);
                Tree path = getPathToNode(subtree, parentVP, root);
                nodesNP.add(new NodeTreeData(path, subtree));
            }
            else  if (isNP(subtree) && !isHasPhrasesNP(subtree) && isHasConnection(subtree, root) != null)
            {
                System.out.println("GET subtree isHasConnection: " + subtree);
                Tree path = getPathToNode(subtree, parentVP, root);
                nodesNP.add(new NodeTreeData(path, subtree));
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

    //NOT Good from architecture side
    public static void investigateAllConnectedNodes(Tree tree, Tree nodeNP,  Tree root, List<NodeTreeData> connectedNodesNP)
    {

        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {

            if(child.equals(nodeNP)) {
                System.out.println("======child: " + child);
                Tree treeVP = TreeHelper.isHasConnection(child, tree);
                if(treeVP != null) {

//                    List<NodeTreeData> connectedNodes = new ArrayList<NodeTreeData>();
//                    TreeHelper.getRelatedNP(treeVP, connectedNodes, root, treeVP);
//                    connectedNodesNP.addAll(connectedNodes);
//                    System.out.println("======treeVP isLeaf: " + treeVP.isLeaf());

                    if (isVP(treeVP.firstChild())) {
                         List<Tree> parentVPS = getIncludedVP(treeVP);
                         for(Tree vpTree: parentVPS) {
                             System.out.println("======vpTree: " + vpTree);
                             List<NodeTreeData> connectedNodes = new ArrayList<NodeTreeData>();
                             TreeHelper.getRelatedNP(vpTree, connectedNodes, root, vpTree);
                             connectedNodesNP.addAll(connectedNodes);
                         }

                     } else {
                         List<NodeTreeData> connectedNodes = new ArrayList<NodeTreeData>();
                         TreeHelper.getRelatedNP(treeVP, connectedNodes, root, treeVP);
                         connectedNodesNP.addAll(connectedNodes);
                     }

                }
            }
            investigateAllConnectedNodes(child, nodeNP, root, connectedNodesNP);
        }
    }

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

        Tree pathDescriptor = getVPDescriptor(parentVP);

        Tree path = getPathFromNodeToNode(paths, nodeNP);
        System.out.println("pathDescriptor: " + pathDescriptor);
        System.out.println("path: " + path);
          if(path == null) {

              System.out.println("pathDescriptor path == null : " + path);
              path = pathDescriptor;
          } else if (pathDescriptor != null) {

              System.out.println("pathDescriptor path else: " + path);
              Tree complexTree = path.deepCopy();
              complexTree.insertDtr(pathDescriptor, 0);
              return complexTree;
          }

        return path;
    }

    public static Tree getPathFromNodeToNode(List<Tree> fullPath, Tree nodeNP)
    {

        Tree path = null;
        for (Tree subtree : fullPath) {
            if(isHasJoinVerb(subtree.value().toString()) && subtree.contains(nodeNP)) {
                System.out.println("SUBTREE: " + subtree);
                return subtree;
            }
        }
        return path;
    }

    public static Tree getVPDescriptor(Tree parentVP)
    {
        Tree tree = null;
        Tree parVP = parentVP.deepCopy();
        if (isVP(parVP.firstChild())) {
            parVP = parVP.firstChild();
        }

        List<Tree> paths = parVP.getChildrenAsList();
        for (Tree child : paths) {
            System.out.println("SUBTREE child: " + child);
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
