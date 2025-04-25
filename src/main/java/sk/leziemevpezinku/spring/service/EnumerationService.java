package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.enums.EnumerationItem;

import java.util.List;

public interface EnumerationService {

    /**
     *
     * @return
     */
    List<EnumerationItem> getVisibleEnumerations();
}
