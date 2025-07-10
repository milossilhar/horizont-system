package sk.leziemevpezinku.spring.api.enumeration;

public interface EnumerationValues {

    interface REG_SHIRT_SIZE {
        String XS = "XS";
        String S = "S";
        String M = "M";
        String L = "L";
        String XL = "XL";
    }

    interface REG_RELATION {
        String MOM = "MOM";
        String DAD = "DAD";
        String SIS = "SIS";
        String BRO = "BRO";
        String GRM = "GRM";
        String GRD = "GRD";
        String UNC = "UNC";
        String AUN = "AUN";
        String OTH = "OTH";
    }

    interface REG_EVENT_TYPE {
        String EVENT = "EVENT";
        String ECA = "ECA";
    }

    interface REG_EVENT_CONDITION_TYPE {
        String YEAR_BORN_RANGE = "YEAR_RANGE";
        String YEAR_BORN_MIN = "YEAR_MIN";
        String YEAR_BORN_MAX = "YEAR_MAX";
        String AGE_MIN = "AGE_MIN";
        String AGE_MAX = "AGE_MAX";
    }
}
