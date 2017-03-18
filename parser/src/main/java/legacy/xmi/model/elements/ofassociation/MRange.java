package legacy.xmi.model.elements.ofassociation;

import java.io.Serializable;
import java.sql.Time;

import javax.xml.bind.annotation.XmlAttribute;

public class MRange {
	@XmlAttribute (name="xmi.id")
	private String _multiplicityRange_id;

	@XmlAttribute (name="lower")
	private String _multiplicityRange_lower;

	@XmlAttribute (name="upper")
	private String _multiplicityRange_upper;

	public MRange() {
		super();

		this._multiplicityRange_id = "r1";
		this._multiplicityRange_lower = "1";
		this._multiplicityRange_upper = "1";
	}
	
	
}
