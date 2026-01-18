package com.employee.enums;

public enum BloodGroup {
    A_Positive,A_Negative,
    AB_Positive,AB_Negative,
    B_Positive,B_Negative,
    O_Positive,O_Negative;

    public static BloodGroup fromString(String key){
        return key==null?null:BloodGroup.valueOf(key);
    }
}
