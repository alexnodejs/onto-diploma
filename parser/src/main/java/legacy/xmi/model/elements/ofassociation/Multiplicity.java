package legacy.xmi.model.elements.ofassociation;

import enums.MultiplicityRangeType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Multiplicity {

	@XmlElement(name="Multiplicity.range", namespace="org.omg.xmi.namespace.UML")
	private MultiplicityRange _multiplicityRange;
	
	@XmlAttribute (name="xmi.id")
	private String _multiplicity_id;

	public Multiplicity(String id, MultiplicityRangeType range) {
		this._multiplicityRange = new MultiplicityRange(id, range);
		this._multiplicity_id = "multiplicity_" + id;
	}
	
	
}
