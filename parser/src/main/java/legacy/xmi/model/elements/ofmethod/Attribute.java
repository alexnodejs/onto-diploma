package legacy.xmi.model.elements.ofmethod;

import legacy.xmi.model.root.elements.AbstractModelElement;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class Attribute {

    @XmlAttribute(name="xmi.id")
    public String _xmi_id;

    @XmlAttribute(name="name")
    public String _name;

    @XmlAttribute(name="visibility")
    public String _visibility = "private";

    @XmlAttribute(name="isSpecification")
    public boolean _isSpecification = false;

    @XmlAttribute(name="ownerScope")
    public String _ownerScope = "instance";

    public Attribute() {

    }

    public Attribute(String id,
                     String name) {
         this._xmi_id = id;
         this._name = name;
    }

}

/*
      <UML:Attribute xmi.id="A.1" name="name" visibility="private"
                     isSpecification="false" ownerScope="instance"/>
 */
