package legacy.xmi.model.elements.ofassociation;

import enums.MultiplicityRangeType;
import enums.Ranges;

import javax.xml.bind.annotation.XmlAttribute;

public class MRange {
	@XmlAttribute (name="xmi.id")
	private String _multiplicityRange_id;

	@XmlAttribute (name="lower")
	private String _multiplicityRange_lower;

	@XmlAttribute (name="upper")
	private String _multiplicityRange_upper;

	public MRange(String id, MultiplicityRangeType range) {
		super();

		String rangeId = "range_" + id;
		Ranges[] ranges = range.getRanges();
		this._multiplicityRange_id = rangeId;
		this._multiplicityRange_lower = ranges[0].getValue().toString();
		this._multiplicityRange_upper = ranges[1].getValue().toString();
	}
	
}
