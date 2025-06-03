package sk.leziemevpezinku.spring.service;

public interface AppParamService {

    /**
     * Checks app param with name = featureName and if there is 'true' or '1' in value considers feature enabled.
     * @param featureName name of the app param
     * @return true for enabled feature, otherwise false
     */
    boolean hasEnabledFeature(String featureName);

    /**
     * Gets numeric value from app param or null if there is no parsable number in the value.
     * @param name name of the app param
     * @return numeric value or null
     */
    Long getNumericValue(String name);
}
