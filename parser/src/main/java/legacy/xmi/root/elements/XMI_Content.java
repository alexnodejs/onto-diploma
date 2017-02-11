package legacy.xmi.root.elements;

import java.io.File;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;

import legacy.xmi.model.root.elements.Model;
import legacy.xmi.model.root.elements.ModelItem;

public class XMI_Content implements Serializable{
	
	@XmlElement (name="Model", namespace="org.omg.xmi.namespace.UML")
	private Model _UML_model;
	
	public XMI_Content(ModelItem _xmi_modelItem) {
		_UML_model=new Model(_xmi_modelItem);
	}

	
}
