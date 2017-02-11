package legacy.xmi.model.elements.ofGeneralization;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.elements.ofassociation.AssociationEndMultiplicity;
import legacy.xmi.model.elements.ofassociation.AssociationParticipant;
import legacy.xmi.model.root.elements.AbstractModelElement;

public class Generalization extends AbstractModelElement{
	@XmlElement(name="Generalization.child", namespace="org.omg.xmi.namespace.UML")
	private GeneralizationChild _generalizationChild;
	@XmlElement(name="Generalization.parent", namespace="org.omg.xmi.namespace.UML")
	private GeneralizationParent _generalizationParent;
	
	@XmlAttribute (name="xmi.id")
	public String _generalization_id;
	@XmlAttribute (name="discriminator")
	private String _discriminator;
	//@XmlAttribute (name="isSpecification")
	//private boolean _isSpecification;
	
	public Generalization(GeneralizationChild _generalizationChild,
			GeneralizationParent _generalizationParent,
			String _generalization_id, String _discriminator, boolean _isSpecification) {
		super();
		
		
		this._generalizationChild = _generalizationChild;
		this._generalizationParent = _generalizationParent;
		this._generalization_id = _generalization_id;
		this._discriminator=_discriminator;
		this._isSpecification = _isSpecification;
		System.out.println(this._generalization_id+"!!!!!!!!!!!!!!!!!!!!!");
	}

	public Generalization(String _generalization_id) {
		super();
		this._generalizationChild = new GeneralizationChild();
		this._generalizationParent = new GeneralizationParent();
		this._generalization_id = _generalization_id;
		this._discriminator="_discriminator";
		this._isSpecification = false;
	}
	
}
