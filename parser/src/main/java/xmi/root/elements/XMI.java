
package xmi.root.elements;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.
import javax.xml.bind.annotation.XmlSchema;

import xmi.model.root.elements.ModelItem;


@XmlRootElement (name="XMI")

public class XMI implements Serializable{

	@XmlAttribute(name="xmi.version")
	private  double _xmi_root_version;
	@XmlElement (name="XMI.header")
	private XMI_Header _xmi_header;
	@XmlElement (name="XMI.content")
	private XMI_Content _xmi_content;
	public XMI(){}
	
	public XMI(ModelItem _xmi_modelItem) {
		this._xmi_root_version=1.2;
		
		this._xmi_header=new XMI_Header();
		
		this._xmi_content=new XMI_Content(_xmi_modelItem);
	}
	
	}
	

