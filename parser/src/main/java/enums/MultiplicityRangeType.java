package enums;


import static enums.Ranges.*;


/**
 * Created by svitlanamoiseyenko on 3/18/17.
 */




public enum MultiplicityRangeType {
    OneToOne(One, One),
    OneToMany(One, Many),
    ZeroToOne(Zero, One),
    ZeroToMany(Zero, Many);

    private Ranges[] ranges;
    private MultiplicityRangeType(Ranges... ranges) {
        this.ranges = ranges;
    }

    public Ranges[] getRanges() {
        return ranges.clone();
    }

}
