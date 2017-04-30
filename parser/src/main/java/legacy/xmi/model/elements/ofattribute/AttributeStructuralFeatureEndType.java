package legacy.xmi.model.elements.ofattribute;

import legacy.xmi.model.elements.ofassociation.MultiplicityRange;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by svitlanamoiseyenko on 3/17/17.
 */
public class AttributeStructuralFeatureEndType {

    @XmlElement(name = "DataType", namespace = "org.omg.xmi.namespace.UML")
    public DataType _dataType;

    public AttributeStructuralFeatureEndType(String attributeType) {
        _dataType = new DataType(attributeType);
    }

}
