package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.api.dto.EventDTO;
import sk.leziemevpezinku.spring.api.mapper.EventMapper;
import sk.leziemevpezinku.spring.repo.EventRepository;
import sk.leziemevpezinku.spring.service.EventService;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceBean implements EventService {

    private final EventMapper mapper;
    private final EventRepository repository;

    @Override
    public EventDTO create(EventDTO eventDTO) {
        var entity = mapper.createEntity(eventDTO);
        var saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<EventDTO> getPage(Pageable pageable) {
        var events = repository.findAll(pageable);
        return mapper.toDTOPage(events);
    }

    @Override
    public List<EventDTO> getAll() {
        var events = repository.findAll();
        return mapper.toDTOList(events);
    }

    @Override
    public EventDTO getOne(Long id) {
        var event = repository.findById(id).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                .parameter("id", id)
                .build()) ;
        return mapper.toDTO(event);
    }

    @Override
    public EventDTO update(EventDTO eventDTO) {
        Objects.requireNonNull(eventDTO.getId(), "Event id cannot be null");

        var event = repository.findById(eventDTO.getId()).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                .parameter("id", eventDTO.getId())
                .build());
        mapper.updateEntity(eventDTO, event);
        var saved = repository.save(event);
        return mapper.toDTO(saved);
    }

    @Override
    public EventDTO getByUuid(String uuid) {
        var event = repository.findByUuid(uuid).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                .parameter("uuid", uuid)
                .build());
        return mapper.toDTO(event);
    }
}
