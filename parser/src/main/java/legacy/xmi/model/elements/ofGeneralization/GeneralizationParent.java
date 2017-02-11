package legacy.xmi.model.elements.ofGeneralization;

import javax.xml.bind.annotation.XmlElement;

public class GeneralizationParent {
	@XmlElement(name="Class", namespace="org.omg.xmi.namespace.UML")
	private GeneralizationParentClass _generalizationParentClass;

	public GeneralizationParent(
			GeneralizationParentClass _generalizationParentClass) {
		super();
		this._generalizationParentClass = _generalizationParentClass;
	}
	public GeneralizationParent(
			) {
		super();
		this._generalizationParentClass = new GeneralizationParentClass();
	}
	
}
