package legacy.xmi.model.elements.ofGeneralization;

import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.elements.ofassociation.AssociationParticipantClass;

public class GeneralizationChild {
	@XmlElement(name="Class", namespace="org.omg.xmi.namespace.UML")
	private GeneralizationChildClass _generalizationChildClass;

	public GeneralizationChild(
			GeneralizationChildClass _generalizationChildClass) {
		//super();
		this._generalizationChildClass = _generalizationChildClass;
		System.out.println("!!!!!!!"+this._generalizationChildClass.toString());
	}

	public GeneralizationChild() {
		//super();
		this._generalizationChildClass =  new GeneralizationChildClass();
	}
	
	
}
