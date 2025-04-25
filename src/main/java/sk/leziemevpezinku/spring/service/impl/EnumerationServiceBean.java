package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.enums.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationItem_;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;
import sk.leziemevpezinku.spring.service.EnumerationService;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EnumerationServiceBean implements EnumerationService {

    private final EnumerationRepository enumerationRepository;

    @Override
    public List<EnumerationItem> getVisibleEnumerations() {
        return enumerationRepository.findByVisible(true, Sort.by(Sort.Direction.ASC, EnumerationItem_.ORDERING));
    }
}
