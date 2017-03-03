import edu.stanford.nlp.trees.Tree;
import graphs.XMINode;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIGraphHelper {

    public static XMINode getXMINode(Tree treeNode) {

        XMINode xmiNode = new XMINode();
        List<Tree> childList = treeNode.getChildrenAsList();
        for (Tree childTree : childList) {
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
}
