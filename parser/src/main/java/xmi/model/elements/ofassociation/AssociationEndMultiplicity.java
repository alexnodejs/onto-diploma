package xmi.model.elements.ofassociation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class AssociationEndMultiplicity  {
	@XmlElement(name="Multiplicity", namespace="org.omg.xmi.namespace.UML")
	private Multiplicity _multiplicity;

	public AssociationEndMultiplicity() {
		
		this._multiplicity = new Multiplicity();
	}
	
	
}
