package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.api.dto.PeriodDTO;
import sk.leziemevpezinku.spring.service.base.CreateService;
import sk.leziemevpezinku.spring.service.base.ReadService;
import sk.leziemevpezinku.spring.service.base.UpdateService;

public interface PeriodService extends
        CreateService<PeriodDTO, PeriodDTO>,
        UpdateService<PeriodDTO, PeriodDTO>,
        ReadService<PeriodDTO, Long> {
    // custom contract here
}
