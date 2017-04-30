package legacy.xmi.model.elements.ofclass;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import legacy.xmi.model.elements.ofattribute.Attribute;
import legacy.xmi.model.elements.ofattribute.Classifier;
import legacy.xmi.model.elements.ofmethod.Operation;
import legacy.xmi.model.root.elements.AbstractModelElement;
import legacy.xmi.root.elements.Visibility;


public class Class extends AbstractModelElement {

	@XmlElement(name = "Classifier.feature", namespace = "org.omg.xmi.namespace.UML")
	public Classifier _classifier;

	@XmlElement(name = "GeneralizableElement.generalization", namespace = "org.omg.xmi.namespace.UML")
	public ClassGeneralizableElementGeneralization _generalizableElementGeneralization;


	@XmlAttribute
	private String visibility;

	@XmlAttribute
	private boolean isActive;

	public Class() {

		set_AbstractModelElement("id_1", "Class1", false, false, false, false);
		this.visibility = Visibility.PUBLIC.getName();
		this.isActive = false;
	}

	public void set_generalizableElementGeneralization(String _gen_idref) {
		this._generalizableElementGeneralization = new ClassGeneralizableElementGeneralization(_gen_idref);
	}

	public Class(String _clssID,
				 String _className,
				 boolean _isSpecification,
				 boolean _isRoot,
				 boolean _isLeaf,
				 boolean _isAbstract,
				 Visibility visibility,
				 boolean isActive,
				 String _gen_idref) {

		this._generalizableElementGeneralization = new ClassGeneralizableElementGeneralization(_gen_idref);


		set_AbstractModelElement(_clssID, _className, _isSpecification, _isRoot, _isLeaf, _isAbstract);
		this.visibility = visibility.getName();
		this.isActive = isActive;
	}

	public Class(String _clssID, String _className) {
		this._generalizableElementGeneralization = null;
		set_AbstractModelElement(_clssID, _className, false, false, false, false);
		this.visibility = Visibility.PUBLIC.getName();
		this.isActive = false;
	}

	public void addAttribute(Attribute attribute) {
		if (_classifier == null) {
			this._classifier = new Classifier();
		}
		_classifier.addAttribute(attribute);
	}

	public void addOperation(Operation operation) {
		if (_classifier == null) {
			this._classifier = new Classifier();
		}
		_classifier.addOperation(operation);
	}

}
