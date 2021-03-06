package legacy.xmi.model.elements.ofassociation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import enums.MultiplicityRangeType;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.root.elements.Visibility;


public class AssociationEnd extends AbstractModelElement {

    @XmlElement(name = "AssociationEnd.multiplicity", namespace = "org.omg.xmi.namespace.UML")
    private AssociationEndMultiplicity _associationMultiplicity;
    @XmlElement(name = "AssociationEnd.participant", namespace = "org.omg.xmi.namespace.UML")
    private AssociationParticipant _associationParticipant;
    @XmlAttribute(name = "xmi.id")
    private String _assocEnd_id;
    @XmlAttribute(name = "visibility")
    private String _visibility;
    @XmlAttribute(name = "isNavigable")
    private boolean _isNavigable;
    @XmlAttribute(name = "ordering")
    private String _ordering;
    @XmlAttribute(name = "aggregation")
    private String _aggregation;
    @XmlAttribute(name = "targetScope")
    private String _targetScope;
    @XmlAttribute(name = "changeability")
    private String _changeability;
    @XmlAttribute(name = "name")
    private String _name;

    public AssociationEnd(String _assocEnd_id,
                          Visibility _visibility,
                          boolean _isSpecification,
                          boolean _isNavigable,
                          String _ordering,
                          String _aggregation,
                          String _targetScope,
                          String _changeability,
                          String _participantID,
                          MultiplicityRangeType _rangeType) {
        //super();

        System.out.print("AssociationEnd 1");
        this._associationParticipant = new AssociationParticipant(new AssociationParticipantClass(_participantID));
        this._assocEnd_id = _assocEnd_id;
        this._associationMultiplicity = new AssociationEndMultiplicity(_assocEnd_id, _rangeType);
        this._visibility = _visibility.getName();
        this._isSpecification = _isSpecification;
        this._isNavigable = _isNavigable;
        this._ordering = _ordering;
        this._aggregation = _aggregation;
        this._targetScope = _targetScope;
        this._changeability = _changeability;
    }

    public AssociationEnd(String _assocEnd_id,
                          Visibility _visibility,
                          boolean _isSpecification,
                          boolean _isNavigable,
                          String _ordering,
                          String _aggregation,
                          String _targetScope,
                          String _changeability,
                          String _participantID,
                          String _name,
                          MultiplicityRangeType _rangeType) {
        //super();
        this._associationParticipant = new AssociationParticipant(new AssociationParticipantClass(_participantID));
        this._assocEnd_id = _assocEnd_id;
        this._associationMultiplicity = new AssociationEndMultiplicity(_assocEnd_id, _rangeType);
        this._visibility = _visibility.getName();
        this._isSpecification = _isSpecification;
        this._isNavigable = _isNavigable;
        this._ordering = _ordering;
        this._aggregation = _aggregation;
        this._targetScope = _targetScope;
        this._changeability = _changeability;
        this._name = _name;
    }


}
