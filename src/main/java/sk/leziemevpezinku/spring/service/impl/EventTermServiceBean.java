package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.EventTerm;
import sk.leziemevpezinku.spring.model.EventTerm_;
import sk.leziemevpezinku.spring.repo.EventTermRepository;
import sk.leziemevpezinku.spring.service.EventTermService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventTermServiceBean implements EventTermService {

    private final EventTermRepository eventTermRepository;

    @Override
    public EventTerm getById(Long eventTermId) {
        Optional<EventTerm> eventTermOptional = eventTermRepository.findLoadedById(eventTermId);

        if (eventTermOptional.isEmpty()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM)
                    .build();
        }

        return eventTermOptional.get();
    }
}
