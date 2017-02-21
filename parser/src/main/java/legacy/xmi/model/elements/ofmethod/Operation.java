package legacy.xmi.model.elements.ofmethod;

import legacy.xmi.model.root.elements.AbstractModelElement;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by svitlanamoiseyenko on 2/21/17.
 */
public class Operation {

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

    public Operation() {

    }

    public Operation(String id,
                     String name) {
        this._xmi_id = id;
        this._name = name;
    }
}
/*
<UML:Operation xmi.id = '-64--88-1-3-18987bfc:15a613b4f45:-8000:0000000000000867'
              name = 'smile' visibility = 'public' isSpecification = 'false' ownerScope = 'instance'
              isQuery = 'false' concurrency = 'sequential' isRoot = 'false' isLeaf = 'false'
              isAbstract = 'false'>
              <UML:BehavioralFeature.parameter>
                <UML:Parameter xmi.id = '-64--88-1-3-18987bfc:15a613b4f45:-8000:0000000000000868'
                  name = 'return' isSpecification = 'false' kind = 'return'/>
              </UML:BehavioralFeature.parameter>
            </UML:Operation>
 */