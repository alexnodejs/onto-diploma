package utils;

import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;
import graphs.*;
import utils.TreeHelper;

import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIGraphHelper {

    public static XMINode getXMINode(NPNode npNode)
    {
        XMINode xmiNode = new XMINode(npNode.id);
        Tree treeNode = npNode.tree;
        List<Tree> childList = treeNode.getChildrenAsList();
        for (Tree childTree : childList)
        {
            if (!childTree.isPhrasal() && TreeHelper.isNoun(childTree.value().toString())) {
                String name = String.valueOf(childTree.getLeaves().get(0));
                if (xmiNode.name == null) {
                    xmiNode.name = (Character.toUpperCase(name.charAt(0))) + name.substring(1);
                } else {
                    xmiNode.name += " " + (Character.toUpperCase(name.charAt(0))) + name.substring(1);
                }
            }

            if (!childTree.isPhrasal() && TreeHelper.isAdjective(childTree.value().toString())) {
                xmiNode.attributes.add(String.valueOf(childTree.getLeaves().get(0)));
            }
        }

        return xmiNode;
    }


    public static XMIEdge getXMIEdge(NPEdge npEdge)
    {
        XMIEdge xmiEdge = new XMIEdge(npEdge.id);
        Tree pathTree = npEdge.path;
        System.out.println("===xmiEdge pathTree==== " + pathTree);

        if(npEdge.path != null) {

            List<Tree> childList = npEdge.path.getChildrenAsList();
            for (Tree childTree : childList) {

                if (childTree.isLeaf()) {

                    xmiEdge.name =  String.valueOf(childTree.getLeaves().get(0));
                    xmiEdge.setType(childTree.value().toString(),"");

                } else {
                    List<LabeledWord> words = childTree.labeledYield();
                    System.out.println("===words==== " + words);

                    for (LabeledWord word : words) {

                        if (TreeHelper.isVerb(word.tag().toString())) {
                            String name = word.word().toString();
                            if (xmiEdge.name == null) {
                                xmiEdge.name = name;
                            } else {
                                xmiEdge.name += " " + name;
                            }
                        }

                        if (!childTree.isPhrasal() && TreeHelper.isAdjective(childTree.value().toString())) {
                            String name = word.word().toString();
                            if (xmiEdge.name == null) {
                                xmiEdge.name = name;
                            } else {
                                xmiEdge.name += " " + name;
                            }
                        }

                        xmiEdge.setType(word.tag().toString(), word.word().toString());
                    }
                }
            }
        }

        xmiEdge.name = xmiEdge.name == null ? "" : xmiEdge.name;
        xmiEdge.seNodesIDS(npEdge.parentNodeId, npEdge.childNodeId);

        return xmiEdge;
    }



}
