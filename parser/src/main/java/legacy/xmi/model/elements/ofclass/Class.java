package legacy.xmi.model.elements.ofclass;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import legacy.xmi.model.elements.ofassociation.AssociationParticipant;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.root.elements.Visibility;


public class Class extends AbstractModelElement{
	@XmlElement(name="GeneralizableElement.generalization", namespace="org.omg.xmi.namespace.UML")
	public ClassGeneralizableElementGeneralization _generalizableElementGeneralization;
	@XmlAttribute 
	private String visibility;
	@XmlAttribute 
	private boolean isActive;

	public Class() {
		set_AbstractModelElement("id_1", "Class1", false,false,false,false);
		this.visibility = Visibility.PUBLIC.getName();
		this.isActive = false;
	}
	
	public void set_generalizableElementGeneralization(String _gen_idref)
	{
		this._generalizableElementGeneralization= new ClassGeneralizableElementGeneralization(_gen_idref);
	}
	public Class(String _clssID, String _className, boolean _isSpecification,
			boolean _isRoot, boolean _isLeaf, boolean _isAbstract, 
			Visibility visibility,boolean isActive,String _gen_idref) {
		this._generalizableElementGeneralization= new ClassGeneralizableElementGeneralization(_gen_idref);
		
		set_AbstractModelElement(_clssID, _className, _isSpecification,_isRoot,_isLeaf,_isAbstract);
		this.visibility = visibility.getName();
		this.isActive = isActive;
	}
	public Class(String _clssID, String _className) {
		this._generalizableElementGeneralization=null;
		set_AbstractModelElement(_clssID, _className, false,false,false,false);
		this.visibility = Visibility.PUBLIC.getName();;
		this.isActive = false;
	}


}
