package legacy;

// Relations: VP, V
// Classes: NNN, N, AN
// Attribute: N, NN, NA
// Operations VN, VNN, V

public enum GramRelationsEnum {
//classElementsBuilder
REL_JJ("jj"),
REL_NN("nn"),

REL_AMOD("amod"), //NP

REL_NSUBJ("nsubj"),
REL_VMOD("vmod"),
REL_RCMOD("rcmod"),
REL_DEP("dep"),
REL_PARTMOD("partmod"),

//associationElementBuilder
REL_AUX("aux"),
REL_AUXPASS("auxpass"),
REL_DOBJ("dobj"), //VP
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
REL_DET("det"),

REL_NMOD("nmod"),
REL_NMOD_NPMOD("nmod:npmod"),


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
    //return REL_OTHER;
    throw new RuntimeException("UNKNOWN TYPE: " + pType);
}

public String getTypeValue() {
    return typeValue;
}

}
