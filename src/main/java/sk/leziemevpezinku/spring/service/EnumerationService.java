package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

import java.util.List;

public interface EnumerationService {

    /**
     * Returns only enumerations with flag visible = true.
     * @return list of visible enumerations
     */
    List<EnumerationItem> getVisibleEnumerations();

    /**
     * Finds description for given enumeration name and code combination.
     * @param name enumeration name
     * @param code code
     * @return found description
     */
    String getDescription(EnumerationName name, String code);
}
