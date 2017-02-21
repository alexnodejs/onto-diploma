package legacy.xmi.model.elements.ofmethod;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class Classifier {

    //TODO list of attr and methods
    @XmlElement(name="Attribute", namespace="org.omg.xmi.namespace.UML")
    public Attribute _attribute;

    @XmlElement(name="Operation", namespace="org.omg.xmi.namespace.UML")
    public Operation _operation;

    public Classifier() {

        //this._attribute = attribute; //new Attribute("0", "KYKY");
        //this._method = method;
    }

    public void addAttribute(Attribute attribute) {
        this._attribute = attribute;
    }

    public void addOperation(Operation operation) {
        this._operation = operation;
    }
}
