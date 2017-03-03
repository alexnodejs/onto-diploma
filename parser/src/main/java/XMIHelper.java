import edu.stanford.nlp.trees.Tree;
import graphs.XMIEdge;
import graphs.XMINode;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofmethod.Attribute;
import legacy.xmi.model.root.elements.AbstractModelElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIHelper {

    public List<AbstractModelElement> abstractModelElements = new ArrayList<AbstractModelElement>();

    private static XMIHelper instance;
    public static synchronized XMIHelper getInstance() {
        if (instance == null) {
            instance = new XMIHelper();
        }
        return instance;
    }


    public int generateIndex()
    {
        int index = abstractModelElements.size() > 0 ? abstractModelElements.size() + 1 : 0;
        return index;
    }

    public boolean isElementExist(AbstractModelElement element) {
        if(!Arrays.asList(abstractModelElements).contains(element)) {
            return false;
        }
        return true;
    }

    public void addElementToClass(AbstractModelElement element)
    {
        if (element == null) {
            return;
        }

        if (! isElementExist(element)) {
            abstractModelElements.add(element);
        }
    }

    public  Class getClassElementFromNode(XMINode xmiNodeNode) {

        int index = generateIndex();
        Class element = ElementBuilderUtil.classElementsBuilder(xmiNodeNode.name, index);
        for(String attribute: xmiNodeNode.attributes) {
            Attribute attr = ElementBuilderUtil.attributeBuilder(attribute, index);
            element.addAttribute(attr);
        }

        return element;
    }

    public Association getAssociationElementFromEdge(XMIEdge xmiEdge, String parentName, String childName) {

        int index = generateIndex();
        Class parent = getClassElement(parentName, SearchType.CLASS_NAME);
        Class child = getClassElement(childName, SearchType.CLASS_NAME);
        Association association = ElementBuilderUtil.associationElementBuilder(xmiEdge.name,parent, child, index);

        return association;
    }

    public Class getClassElement(String name, SearchType searchType)
    {
        for (AbstractModelElement element: abstractModelElements) {
            if (element instanceof Class) {
                switch (searchType) {

                    case CLASS_ATTRIBUTE:
                        if (((Class)element).getClassAttributeByName(name.toLowerCase()) != null) {
                            return  (Class) element;
                        }
                        break;

                    case CLASS_NAME:
                        if (element._model_name.toLowerCase().equals(name.toLowerCase())) {
                            return  (Class) element;
                        }

                        break;

                    case CLASS_OPERATION:
                        if (((Class)element).getClassOperationByName(name.toLowerCase()) != null) {
                            return  (Class) element;
                        }
                        break;
                }
            }
        }
        return  null;
    }

}