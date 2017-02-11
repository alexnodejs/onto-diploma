package legacy.xmi.model.root.elements;

import java.io.Serializable;

import legacy.xmi.root.elements.Visibility;

import javax.xml.bind.annotation.XmlAttribute;

public class AbstractModelElement implements Serializable{
	@XmlAttribute (name="xmi.id")
	public String _model_id;
	@XmlAttribute (name="name")
	public String _model_name;
	@XmlAttribute (name="isSpecification")
	public boolean _isSpecification;
	@XmlAttribute (name="isRoot")
	public boolean _isRoot;
	@XmlAttribute (name="isLeaf")
	public boolean _isLeaf;
	@XmlAttribute (name="isAbstract")
	public boolean _isAbstract;
	
	
	public void set_AbstractModelElement(String _model_id,String _model_name,boolean _isSpecification,boolean _isRoot,boolean _isLeaf,boolean _isAbstract) {
		this._model_id = _model_id;
		this._model_name = _model_name;
		this._isSpecification = _isSpecification;
		this._isRoot = _isRoot;
		this._isLeaf = _isLeaf;
		this._isAbstract = _isAbstract;		
	}

		
}
