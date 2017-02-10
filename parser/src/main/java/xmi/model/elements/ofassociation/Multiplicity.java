package xmi.model.elements.ofassociation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Multiplicity {
	@XmlElement(name="Multiplicity.range", namespace="org.omg.xmi.namespace.UML")
	private MultiplicityRange _multiplicityRange;
	
	@XmlAttribute (name="xmi.id")
	private String _multiplicity_id;

	public Multiplicity() {
		
		this._multiplicityRange = new MultiplicityRange();
		this._multiplicity_id = "mult1";
	}
	
	
}
