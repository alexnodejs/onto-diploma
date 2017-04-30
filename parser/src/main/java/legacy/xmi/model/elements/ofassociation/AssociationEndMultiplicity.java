package legacy.xmi.model.elements.ofassociation;

import enums.MultiplicityRangeType;

import javax.xml.bind.annotation.XmlElement;

public class AssociationEndMultiplicity  {

	@XmlElement(name="Multiplicity", namespace="org.omg.xmi.namespace.UML")
	private Multiplicity _multiplicity;

	public AssociationEndMultiplicity(String id, MultiplicityRangeType rangeType) {
		this._multiplicity = new Multiplicity(id, rangeType);
	}
	
	
}
