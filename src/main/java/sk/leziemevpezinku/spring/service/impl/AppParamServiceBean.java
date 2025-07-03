package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.model.AppParam;
import sk.leziemevpezinku.spring.repo.AppParamRepository;
import sk.leziemevpezinku.spring.service.AppParamService;
import sk.leziemevpezinku.spring.api.exception.AppParamNotFound;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppParamServiceBean implements AppParamService {

    private final AppParamRepository appParamRepository;

    @Override
    @Transactional
    public boolean hasEnabledFeature(String featureName) {
        String value = getValue(featureName);

        return "true".equals(StringUtils.lower(value)) || "1".equals(value);
    }

    @Override
    @Transactional
    public Long getNumericValue(String name) {
        String value = getValue(name);
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            log.warn("APP_PARAM {} does not have parsable Long value: {}", name, value);
            return null;
        }
    }

    private String getValue(String name) {
        return findAppParam(name).getValue();
    }

    private AppParam findAppParam(String name) {
        Optional<AppParam> optionalAppParam = this.appParamRepository.findById(name);

        if (optionalAppParam.isEmpty()) throw new AppParamNotFound(name);

        return optionalAppParam.get();
    }
}
