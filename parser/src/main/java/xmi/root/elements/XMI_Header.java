package xmi.root.elements;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;



public class XMI_Header implements Serializable{
	@XmlElement (name="XMI.metamodel")
	private XMI_Metamodel _xmi_metamodel=new XMI_Metamodel();
}
