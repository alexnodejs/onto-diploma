package legacy.xmi.model.root.elements;

import javax.xml.bind.annotation.XmlElement;

public class Model extends AbstractModelElement  {
	
	@XmlElement(name="Namespace.ownedElement",namespace="org.omg.xmi.namespace.UML")
	private ModelItem _modelItem;
	
	public Model(ModelItem _xmi_modelItem) {		
		set_AbstractModelElement("UML_model_1", "NewModelUML", false,false,false,false);
		_modelItem=_xmi_modelItem;//new ModelItem();
	}

}
