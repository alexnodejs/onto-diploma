import edu.stanford.nlp.trees.Tree;
import graphs.XMINode;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofmethod.Attribute;
import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.Arrays;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIHelper {

    public static int generateIndex(List<AbstractModelElement> abstractModelElements)
    {
        int index = abstractModelElements.size() > 0 ? abstractModelElements.size() + 1 : 0;
        return index;
    }

    public static boolean isElementExist(List<AbstractModelElement> abstractModelElements,
                                         Class element) {
        if(!Arrays.asList(abstractModelElements).contains(element)) {
            return false;
        }
        return true;
    }

    public static void addElementToClass(Class element, List<AbstractModelElement> abstractModelElements)
    {
        if (element == null) {
            return;
        }

        if (! isElementExist(abstractModelElements, element)) {
            abstractModelElements.add(element);
        }
    }

    public static Class getClassElementFromNode(XMINode xmiNodeNode, int index) {

        //int index = TextParserHelper.generateIndex(abstractModelElements);
        Class element = ElementBuilderUtil.classElementsBuilder(xmiNodeNode.name, index);
        for(String attribute: xmiNodeNode.attributes) {
            Attribute attr = ElementBuilderUtil.attributeBuilder(attribute, index);
            element.addAttribute(attr);
        }

        return element;
    }
}
