package xmi.model.root.elements;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Model extends AbstractModelElement  {
	
	@XmlElement(name="Namespace.ownedElement",namespace="org.omg.xmi.namespace.UML")
	private ModelItem _modelItem;
	
	public Model(ModelItem _xmi_modelItem) {		
		set_AbstractModelElement("UML_model_1", "NewModelUML", false,false,false,false);
		_modelItem=_xmi_modelItem;//new ModelItem();
	}

}
