package xmi.model.elements.ofGeneralization;

import javax.xml.bind.annotation.XmlAttribute;

public class GeneralizationChildClass {
	@XmlAttribute (name="xmi.idref")
	private String _generalizationChildClass_idref;

	public GeneralizationChildClass(String _generalizationChildClass_idref) {
		//super();
		this._generalizationChildClass_idref = _generalizationChildClass_idref;
		System.out.println(this._generalizationChildClass_idref);
	}
	public GeneralizationChildClass() {
		//super();
		this._generalizationChildClass_idref = "genClass1" ;
	}
	
}
