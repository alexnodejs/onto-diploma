package xmi.model.elements.ofassociation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class MultiplicityRange{
	@XmlElement(name="MultiplicityRange", namespace="org.omg.xmi.namespace.UML")
	private MRange _mRange;

	public MultiplicityRange() {
		this._mRange=new MRange();
	}
	
	
}
