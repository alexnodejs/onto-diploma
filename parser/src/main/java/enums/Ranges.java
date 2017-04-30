package enums;

/**
 * Created by svitlanamoiseyenko on 3/19/17.
 */
public enum Ranges {
    One("1"), Zero("0"), Many("-1");

    private String value;
    private Ranges(String val) {
        value = val;
    }
    public String getValue() {
        return value;
    }
}