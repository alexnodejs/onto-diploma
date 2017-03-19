package utils.xmi;

import graphs.XMIRelationType;
import graphs.XMIEdge;
import graphs.XMINode;
import legacy.xmi.model.elements.ofGeneralization.Generalization;
import legacy.xmi.model.elements.ofassociation.Association;
import legacy.xmi.model.elements.ofclass.Class;
import legacy.xmi.model.elements.ofattribute.Attribute;
import legacy.xmi.model.root.elements.AbstractModelElement;
import utils.ElementBuilderUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 3/3/17.
 */
public class XMIUtil {

    public List<AbstractModelElement> abstractModelElements = new ArrayList<AbstractModelElement>();

    private static XMIUtil instance;
    public static synchronized XMIUtil getInstance() {
        if (instance == null) {
            instance = new XMIUtil();
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
            System.out.println("===npTreeAttributes==== " + attr._name);
            element.addAttribute(attr);
        }

        return element;
    }

    //Decorator
    public AbstractModelElement getConnectionElement(XMIEdge xmiEdge,  XMINode parentNode, XMINode childNode)
    {
        int index = generateIndex();
        Class parent = getClassElement(parentNode.name);
        Class child = getClassElement(childNode.name);
        System.out.println("===xmiEdge name==== " + xmiEdge.name);

        Association association;
        if(xmiEdge.XMIRelationType == XMIRelationType.GENERALIZATION) {

            Generalization generalization;
            generalization = ElementBuilderUtil.genearlizationElementBuilder(parent, child, index);
            return generalization;
        } else if(xmiEdge.XMIRelationType == XMIRelationType.AGGREGATION) {

            association = ElementBuilderUtil
                    .aggregationElementBuilder("", parent, child, xmiEdge.name, "", parentNode.range, childNode.range, index);
            return association;
        } else  {

            association = ElementBuilderUtil.associationElementBuilder(xmiEdge.name, parent, child, parentNode.range, childNode.range, index);
            return association;
        }
    }


    public Class getClassElement(String name)
    {
        for (AbstractModelElement element: abstractModelElements) {
            if (element instanceof Class) {
                if (element._model_name.toLowerCase().equals(name.toLowerCase())) {
                            return  (Class) element;
                }
            }
        }
        return  null;
    }

}
