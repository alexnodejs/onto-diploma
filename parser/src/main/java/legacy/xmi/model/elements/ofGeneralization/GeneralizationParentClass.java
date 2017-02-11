package legacy.xmi.model.elements.ofGeneralization;

import javax.xml.bind.annotation.XmlAttribute;

public class GeneralizationParentClass {
	@XmlAttribute (name="xmi.idref")
	private String _generalizationParentClass_idref;

	public GeneralizationParentClass(String _generalizationParentClass_idref) {
		super();
		this._generalizationParentClass_idref = _generalizationParentClass_idref;
	}

	public GeneralizationParentClass() {
		super();
		this._generalizationParentClass_idref = "genClass2";
	}
	
	
}
