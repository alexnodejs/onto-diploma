package legacy.xmi.model.elements.ofassociation;

import enums.MultiplicityRangeType;

import javax.xml.bind.annotation.XmlElement;

public class MultiplicityRange {

	@XmlElement(name="MultiplicityRange", namespace="org.omg.xmi.namespace.UML")
	private MRange _mRange;

	public MultiplicityRange(String id, MultiplicityRangeType range) {
		this._mRange = new MRange(id, range);
	}
	
	
}
