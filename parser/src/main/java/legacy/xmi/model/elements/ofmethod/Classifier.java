package legacy.xmi.model.elements.ofmethod;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class Classifier {


    @XmlElement(name="Operation", namespace="org.omg.xmi.namespace.UML")
    public Operation _operation;

    @XmlElement(name="Attribute", namespace="org.omg.xmi.namespace.UML")
    public List<Attribute> attributes;

    public Classifier() {
        attributes = new ArrayList<Attribute>();
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addOperation(Operation operation) {
        this._operation = operation;
    }
}
