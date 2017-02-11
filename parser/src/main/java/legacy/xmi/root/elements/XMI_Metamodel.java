package legacy.xmi.root.elements;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;

public class XMI_Metamodel implements Serializable{

 @XmlAttribute (name="xmi.name")
 private String _xmi_metamodel_name="UML";
 
 @XmlAttribute (name="xmi.version")
 private double _xmi_metamodel_version=1.4;
 
}
