package legacy.xmi.model.elements.ofassociation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.root.elements.AbstractModelElement;

public class AssociationParticipant {

    @XmlElement(name = "Class", namespace = "org.omg.xmi.namespace.UML")
    private AssociationParticipantClass _associationClassParticipant;

    public AssociationParticipant(AssociationParticipantClass _associationClassParticipant) {
        //super();
        this._associationClassParticipant = _associationClassParticipant;
    }

}
