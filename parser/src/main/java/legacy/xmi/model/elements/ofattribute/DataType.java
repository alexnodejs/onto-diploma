package legacy.xmi.model.elements.ofattribute;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by svitlanamoiseyenko on 3/17/17.
 */
public class DataType {

    private final String H_REF = "http://argouml.org/profiles/uml14/default-uml14.xmi#";

    @XmlAttribute
    private String _href;

    public DataType(String attributeType) {
        this._href = H_REF + attributeType;
    }

}
