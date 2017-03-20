package utils.tree;

import classes.NodeTreeData;
import config.Constants;
import edu.stanford.nlp.trees.Tree;

import java.util.*;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class BaseTreeUtil {

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

    public static boolean isAggregation(String tag, String word) {
        //if (Arrays.asList(Constants.aggregationSet).contains(tag) && !Arrays.asList(Constants.generalizationSetWords).contains(word)) {
        if (Arrays.asList(Constants.aggregationSet).contains(tag)) {

                return true;
        }
        return false;
    }

    public static boolean isGeneralization(String tag, String word) {
//        if (Arrays.asList(Constants.generalizationSet).contains(tag) &&
//            Arrays.asList(Constants.generalizationSetWords).contains(word)) {
        if (Arrays.asList(Constants.generalizationSet).contains(tag)) {
            return true;
        }
        return false;
    }


    public static boolean isAdjective(String tag) {
        if (Arrays.asList(Constants.adjectiveSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isNoun(String tag) {
        if (Arrays.asList(Constants.singleNounSet).contains(tag) ||
                Arrays.asList(Constants.pluralNounSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isSingleNoun(String tag) {
        if (Arrays.asList(Constants.singleNounSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isPluralNoun(String tag) {
        if (Arrays.asList(Constants.pluralNounSet).contains(tag)) {
            return true;
        }
        return false;
    }


    public static boolean isVerb(String tag) {
        if (Arrays.asList(Constants.verbSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static  boolean isConj(String tag) {
        if (Arrays.asList(Constants.conjVerbSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isHasJoinVerb(String tag) {
        if (Arrays.asList(Constants.joinVerbSet).contains(tag)) {
            return true;
        }
        return false;
    }

    public static boolean isHasPhrasesNP(Tree tree) {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {
            if (child.isPhrasal() && isNP(child)) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isHasPhrases(Tree tree) {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {
            if (child.isPhrasal()) {
                return true;
            }
        }
        return false;
    }

    protected static Tree isHasRelation(Tree child, Tree root)
    {
        //System.out.println("======NP child: " + child);
        List<Tree> siblings = child.siblings(root);
        //Tree parent = child.parent(root);
        //List<Tree> siblings = parent.getChildrenAsList();

        //System.out.println("======siblings: " + siblings);
        for (Tree sibling : siblings) {

            if(isVP(sibling)) {
                return sibling;
            }
        }
        return null;
    }

    public static void getNPTrees(Tree tree, List<Tree> nodesNP) {
        List<Tree> subtrees = tree.getChildrenAsList();
        for (Tree subtree : subtrees) {
            if (BaseTreeUtil.isNP(subtree) && !BaseTreeUtil.isHasPhrasesNP(subtree)) {
                nodesNP.add(subtree);
            }
            getNPTrees(subtree, nodesNP);
        }
    }

    public static void getRelatedNP(Tree tree, Tree nodeNP, Tree root, List<NodeTreeData> connectedNodesNP) {
        List<Tree> children = tree.getChildrenAsList();
        for (Tree child : children) {

            if (child.equals(nodeNP)) {
                //System.out.println("======getRelatedNP: " + child);
                Tree treeVP = isHasRelation(child, tree);

                //System.out.println("======treeVP: " + treeVP);
                if (treeVP != null) {


                    if (!VPUtil.hasConjVP(treeVP) && isVP(treeVP.firstChild())) {//
                        System.out.println("======treeVP: " + treeVP);
                        List<Tree> parentVPS = VPUtil.getIncludedVP(treeVP);
                        for (Tree vpTree : parentVPS) {
                            List<NodeTreeData> connectedNodes = new ArrayList<NodeTreeData>();
                            NPUtil.getNPwithPath(vpTree, connectedNodes, root, vpTree);
                            connectedNodesNP.addAll(connectedNodes);
                        }

                    } else {
                        List<NodeTreeData> connectedNodes = new ArrayList<NodeTreeData>();
                        NPUtil.getNPwithPath(treeVP, connectedNodes, root, treeVP);
                        connectedNodesNP.addAll(connectedNodes);
                   }

                }
            }
            getRelatedNP(child, nodeNP, root, connectedNodesNP);
        }
    }



}
