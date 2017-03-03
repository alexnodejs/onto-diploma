import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.trees.Tree;
import graphs.NPEdge;
import graphs.NPNode;
import graphs.XMIEdge;
import graphs.XMINode;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;

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
                    xmiNode.name += (Character.toUpperCase(name.charAt(0))) + name.substring(1);
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
        System.out.println("===npTree==== " + pathTree);

        List<Tree> childList = npEdge.path.getChildrenAsList();
        for (Tree childTree : childList) {
            List<LabeledWord> words = childTree.labeledYield();
            System.out.println("===words==== " + words);
            for (LabeledWord word : words) {

                if(TreeHelper.isMainVerb(word.tag().toString())) {

                    String name = word.word().toString();
                    if (xmiEdge.name == null) {
                        xmiEdge.name = name;
                    } else {
                        xmiEdge.name += name;
                    }
                }
            }
            /*if (!childTree.isPhrasal() && TreeHelper.isHasJoinVerb(childTree.value().toString())) {
                System.out.println("===childTree leaves==== " + childTree.getLeaves());
                String name = String.valueOf(childTree.getLeaves().get(0));
                if (xmiEdge.name == null) {
                    xmiEdge.name = name;
                } else {
                    xmiEdge.name += name;
                }
            }*/
        }
        xmiEdge.name = xmiEdge.name == null ? "": xmiEdge.name;
        xmiEdge.type = "none";
        xmiEdge.padentNodeId = npEdge.parentNodeId;
        xmiEdge.childNodeId = npEdge.childNodeId;
        return xmiEdge;
    }

    //public static Class getXMIRepresentationsEdge(Tree parent, Tree child, List<AbstractModelElement> abstractModelElements) {

    //String className = "";
    ///System.out.println(graph.getEdgeSource(edge) + " --> " + graph.getEdgeTarget(edge));
    //List<Tree> childList = treeNode.getChildrenAsList();
//        for (Tree childTree : childList) {
//            if (!childTree.isPhrasal() && TreeHelper.isNoun(childTree.value().toString())) {
//                className += childTree.getLeaves().get(0);
//            }
//        }

    //int index = TextParserHelper.generateIndex(abstractModelElements);
    //return ElementBuilderUtil.associationElementBuilder();
    // }
}
