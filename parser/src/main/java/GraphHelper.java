import edu.stanford.nlp.trees.Tree;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;


import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/28/17.
 */
public class GraphHelper {

    public static Class getXMIRepresentationsNode(Tree treeNode, List<AbstractModelElement> abstractModelElements) {

        String className = "";
        String attribute = "";
        List<Tree> childList = treeNode.getChildrenAsList();
        for (Tree childTree : childList) {
             if (!childTree.isPhrasal() && TreeHelper.isNoun(childTree.value().toString())) {
                 className += childTree.getLeaves().get(0);
             }
             if (!childTree.isPhrasal() && TreeHelper.isAdjective(childTree.value().toString())) {
                attribute += childTree.getLeaves().get(0);
            }
        }

        int index = XMIHelper.generateIndex(abstractModelElements);
        return ElementBuilderUtil.classElementsBuilder(className, index);
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
