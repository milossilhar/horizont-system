package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.api.dto.EventTermDTO;
import sk.leziemevpezinku.spring.mapper.EventTermMapper;
import sk.leziemevpezinku.spring.repo.EventRepository;
import sk.leziemevpezinku.spring.repo.EventTermRepository;
import sk.leziemevpezinku.spring.service.EventTermService;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventTermServiceBean implements EventTermService {

    private final EventTermMapper mapper;
    private final EventTermRepository repository;
    private final EventRepository eventRepository;

    @Override
    public EventTermDTO create(EventTermDTO eventTermDTO) {
        var eventTerm = mapper.createEntity(eventTermDTO);
        var event = eventRepository.findById(eventTermDTO.getEventId()).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                .parameter("id", eventTermDTO.getEventId())
                .build());

        eventTerm.setEvent(event);
        event.getTerms().add(eventTerm);

        var saved = repository.save(eventTerm);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EventTermDTO> getAllByParent(Long eventId) {
        var eventTerms = repository.findByEventId(eventId);
        return mapper.toDTOList(eventTerms);
    }

    @Override
    public List<EventTermDTO> getAll() {
        var eventTerms = repository.findAll();
        return mapper.toDTOList(eventTerms);
    }

    @Override
    public EventTermDTO getOne(Long id) {
        var eventTerm = repository.findById(id).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM)
                .parameter("id", id)
                .build());
        return mapper.toDTO(eventTerm);
    }

    @Override
    public EventTermDTO update(EventTermDTO eventTermDTO) {
        Objects.requireNonNull(eventTermDTO.getId(), "EventTerm id cannot be null");

        var dbEventTerm = repository.findById(eventTermDTO.getId()).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM)
                .parameter("id", eventTermDTO.getId())
                .build());

        mapper.updateEntity(eventTermDTO, dbEventTerm);

        var savedEventTerm = repository.save(dbEventTerm);

        return mapper.toDTO(savedEventTerm);
    }
}
