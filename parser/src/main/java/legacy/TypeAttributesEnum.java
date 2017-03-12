package legacy;

import legacy.xmi.model.elements.ofassociation.Association;

/**
 * Created by svitlanamoiseyenko on 3/12/17.
 */
public enum TypeAttributesEnum {
    TYPE_MODEL("uml:Model"),
    TYPE_CLASS("uml:Class"),
    TYPE_ASSOCIATION("uml:association");

    private String typeValue;

    private TypeAttributesEnum(String type) {
        typeValue = type;
    }

    public static TypeAttributesEnum getType(String pType) {

        for (TypeAttributesEnum type: TypeAttributesEnum.values()) {
            if (type.getTypeValue().equals(pType)) {
                return type;
            }
        }

        throw new RuntimeException("UNKNOWN TYPE: " + pType);
    }
    public String getTypeValue() {
        return typeValue;
    }
}
