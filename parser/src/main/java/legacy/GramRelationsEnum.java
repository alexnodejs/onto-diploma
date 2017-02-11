package legacy;


public enum GramRelationsEnum {
REL_JJ("jj"),
REL_NN("nn"),
REL_AMOD("amod"),
REL_NSUBJ("nsubj"),
REL_VMOD("vmod"),
REL_RCMOD("rcmod"),
REL_DEP("dep"),
REL_AUX("aux"),
REL_AUXPASS("auxpass"),
REL_DOBJ("dobj"),
REL_NSUBJPASS("nsubjpass"),
REL_XCOMP("xcomp"),
REL_AGENT("agent"),
REL_ADVCI("advci"),
REL_PREPAS("prep_as"),
REL_ADVMOD("advmod"),
REL_PREPOF("prep_of"),
REL_CONJOR("conj_or"),
REL_COP("cop"),
REL_PREPTO("prep_to"),
REL_PARTMOD("partmod"),
REL_DET("det"),
REL_OTHER("other"); //added

private String typeValue;

private GramRelationsEnum(String type) {
    typeValue = type;
}

public static GramRelationsEnum getType(String pType) {
	System.out.println("GramRelationsEnum getType");
    for (GramRelationsEnum type: GramRelationsEnum.values()) {
        if (type.getTypeValue().equals(pType)) {
            return type;
        }
    }
    return REL_OTHER;
   // throw new RuntimeException("unknown type"+pType);
}

public String getTypeValue() {
    return typeValue;
}

}
