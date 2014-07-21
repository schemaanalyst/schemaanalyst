package org.schemaanalyst.logic;

/**
 * Created by phil on 18/07/2014.
 */
public enum ThreeVL {

    TRUE, FALSE, UNKNOWN;

    public static final ThreeVL[] VALUES = {TRUE, FALSE, UNKNOWN};

    public boolean isTrue() {
        return this == TRUE;
    }

    public boolean isFalse() {
        return this == FALSE;
    }

    public boolean isUnknown() {
        return this == UNKNOWN;
    }

    public static ThreeVL toThreeVL(Boolean truthValue) {
        if (truthValue == true) {
            return TRUE;
        } else if (truthValue == false) {
            return FALSE;
        } else {
            return UNKNOWN;
        }
    }
}
