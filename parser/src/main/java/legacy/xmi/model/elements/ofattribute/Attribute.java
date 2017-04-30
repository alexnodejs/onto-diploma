package legacy.xmi.model.elements.ofattribute;

import legacy.xmi.model.root.elements.AbstractModelElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

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

    @XmlElement(name = "StructuralFeature.type", namespace = "org.omg.xmi.namespace.UML")
    public AttributeStructuralFeatureEndType _featureType;

    public Attribute(String attributeType) {
        this._featureType = new AttributeStructuralFeatureEndType(attributeType);
    }

    public Attribute(String id, String name, String attributeType) {
         this._xmi_id = id;
         this._name = name;
         this._featureType = new AttributeStructuralFeatureEndType(attributeType);
    }

}

/*
      <UML:Attribute xmi.id="A.1" name="name" visibility="private"
                     isSpecification="false" ownerScope="instance"/>

     <UML:Attribute xmi.id = '-64--88-1-3-3730d96a:15ad8fc5cb0:-8000:0000000000000A7C'
              name = 'teethDescription' visibility = 'public' isSpecification = 'false'
              ownerScope = 'instance' changeability = 'changeable' targetScope = 'instance'>
              <UML:StructuralFeature.multiplicity>
                <UML:Multiplicity xmi.id = '-64--88-1-3-3730d96a:15ad8fc5cb0:-8000:0000000000000A7D'>
                  <UML:Multiplicity.range>
                    <UML:MultiplicityRange xmi.id = '-64--88-1-3-3730d96a:15ad8fc5cb0:-8000:0000000000000A7E'
                      lower = '1' upper = '1'/>
                  </UML:Multiplicity.range>
                </UML:Multiplicity>
              </UML:StructuralFeature.multiplicity>
              <UML:StructuralFeature.type>
                <UML:DataType href = 'http://argouml.org/profiles/uml14/default-uml14.xmi#-84-17--56-5-43645a83:11466542d86:-8000:000000000000087C'/>
              </UML:StructuralFeature.type>
      </UML:Attribute>
 */
