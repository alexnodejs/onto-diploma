package legacy.xmi.model.elements.ofclass;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.elements.ofassociation.AssociationParticipantClass;

public class ClassGeneralizableElementGeneralization implements Serializable {

	@XmlElement(name="Generalization", namespace="org.omg.xmi.namespace.UML")
	private ClassGeneralizableElementGeneralizationGeneralization _generalization;

	public ClassGeneralizableElementGeneralization(String _gen_idref) {
		
		this._generalization = new ClassGeneralizableElementGeneralizationGeneralization(_gen_idref);
	}
	
}
