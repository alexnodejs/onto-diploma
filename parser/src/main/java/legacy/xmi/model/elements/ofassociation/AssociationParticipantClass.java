package legacy.xmi.model.elements.ofassociation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.root.elements.AbstractModelElement;

public class AssociationParticipantClass {

    @XmlAttribute(name = "xmi.idref")
    private String _associationClassParticipant_idref;

    public AssociationParticipantClass(String _associationClassParticipant_idref) {
        //super();
        this._associationClassParticipant_idref = _associationClassParticipant_idref;
    }


}
