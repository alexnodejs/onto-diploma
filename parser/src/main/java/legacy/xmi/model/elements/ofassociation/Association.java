package legacy.xmi.model.elements.ofassociation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.root.elements.Visibility;

public class Association extends AbstractModelElement{
	@XmlElementWrapper(name="Association.connection",namespace="org.omg.xmi.namespace.UML")
	@XmlElement(name="AssociationEnd",namespace="org.omg.xmi.namespace.UML")
    public List<AssociationEnd> _associationEnds = new ArrayList<AssociationEnd>();
	public Association(String _model_id, String _model_name, boolean _isSpecification,
			boolean _isRoot, boolean _isLeaf,boolean  _isAbstract,
			String _assocEnd1,String _assocEnd2,Visibility _visibilityEnd1, Visibility _visibilityEnd2,
			boolean _isSpecificationEnd1,boolean _isSpecificationEnd2, boolean _isNavigableEnd1,
			boolean _isNavigableEnd2, String _orderingEnd1, String _orderingEnd2,
			String _aggregationEnd1, String _aggregationEnd2,String _targetScopeEnd1,String _targetScopeEnd2,
			String _changeabilityEnd1, String _changeabilityEnd2,String _participantID1, String _participantID2) {
		
		this.set_AbstractModelElement(_model_id, _model_name, _isSpecification, _isRoot, _isLeaf, _isAbstract);
		this._associationEnds.add(new AssociationEnd(_assocEnd1,_visibilityEnd1,_isSpecificationEnd1,_isNavigableEnd1,_orderingEnd1,_aggregationEnd1,_targetScopeEnd1,_changeabilityEnd1,_participantID1));
		this._associationEnds.add(new AssociationEnd(_assocEnd2,_visibilityEnd2,_isSpecificationEnd2,_isNavigableEnd2,_orderingEnd1,_aggregationEnd2,_targetScopeEnd2,_changeabilityEnd2,_participantID2));
		
	}
	
	public Association(String _model_id, String _model_name,
						String _participantID1, String _participantID2,int i) {


		this.set_AbstractModelElement(_model_id, _model_name, false, false, false, false);
		this._associationEnds.add(new AssociationEnd("_assocEndFor_"+_participantID1+i,Visibility.PUBLIC,false,true,"unordered","none","instance","changeable",_participantID1));
		this._associationEnds.add(new AssociationEnd("_assocEndFor_"+_participantID2+i,Visibility.PUBLIC,false,true,"unordered","none","instance","changeable",_participantID2));

	}

	public Association(String _model_id,
					   String _model_name,
					   String _participantID1,
					   String _participantID2,
					   String _aggregationID1,
					   String _aggregationID2,
					   String _nameID1,
					   String _nameID2,
					   int i


	) {

		this.set_AbstractModelElement(_model_id, _model_name, false, false, false, false);
		this._associationEnds.add(new AssociationEnd("_assocEndFor_"+_participantID1+i,Visibility.PUBLIC,false,true,"unordered",_aggregationID1,"instance","changeable",_participantID1, _nameID1));
		this._associationEnds.add(new AssociationEnd("_assocEndFor_"+_participantID2+i,Visibility.PUBLIC,false,true,"unordered",_aggregationID2,"instance","changeable",_participantID2, _nameID2));

	}
	
	public void Set_AssociationEnd1 (String _participantID1,int i)
	{
		this._associationEnds.set(0, new AssociationEnd("_assocEndFor_"+_participantID1+i,Visibility.PUBLIC,false,true,"unordered","none","instance","changeable",_participantID1));
	}
}
