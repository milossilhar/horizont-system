package sk.leziemevpezinku.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.repo.EventTermRepository;
import sk.leziemevpezinku.spring.repo.PersonRepository;
import sk.leziemevpezinku.spring.repo.RegistrationRepository;
import sk.leziemevpezinku.spring.repo.UserRepository;
import sk.leziemevpezinku.spring.service.RegistrationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class RegistrationServiceBean implements RegistrationService {

    private final EventTermRepository eventTermRepository;
    private final PersonRepository personRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public List<Registration> createRegistration(Long eventTermId, User user) {
        LocalDateTime now = LocalDateTime.now();

        Optional<EventTerm> eventTermOptional = eventTermRepository.findById(eventTermId);

        if (eventTermOptional.isEmpty()) throw CommonException.builder().errorCode(ErrorCode.MSG_NOT_FOUND_EVENT_TERM).build();

        EventTerm eventTerm = eventTermOptional.get();

        // check event registration window
        Event event = eventTerm.getEvent();

        if (now.isBefore(event.getRegStartAt())) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_SOON)
                    .build();
        }

        if (now.isAfter(event.getRegEndAt())) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_REG_DEADLINE)
                    .build();
        }

        // check if there is same registration
        for (Registration registration : eventTerm.getRegistrations()) {
            if (user.hasPerson(registration.getPerson())) {
                throw CommonException.builder()
                        .errorCode(ErrorCode.MSG_REG_ALREADY_EXISTS)
                        .parameters(Collections.singletonMap("fullname", registration.getPerson().getFullname()))
                        .build();
            }
        }

        // TODO - determine status based on conditions
        RegistrationStatus registrationStatus = RegistrationStatus.WAITING;

        // check if user already created and populate list of saved people to register
        Optional<User> savedUserOptional = userRepository.findByEmail(user.getEmail());

        User savedUser;
        List<Person> savedPeopleToRegister = new ArrayList<>();

        if (savedUserOptional.isEmpty()) {
            // user is not in the DB -> create user with people
            savedUser = userRepository.save(user);
            savedPeopleToRegister.addAll(savedUser.getPeople());
        } else {
            // user is already in the DB -> update user and update people / add missing
            savedUser = savedUserOptional.get();

            List<Person> peopleToSave = new ArrayList<>();
            for (Person person : user.getPeople()) {
                Optional<Person> savedPersonOptional = savedUser.getPerson(person);

                if (savedPersonOptional.isEmpty()) {
                    person.setParent(savedUser);
                    peopleToSave.add(person);
                    continue;
                }

                savedPeopleToRegister.add(savedPersonOptional.get());
            }

            List<Person> savedPersonList = peopleToSave.stream().map(personRepository::save).toList();
            savedPeopleToRegister.addAll(savedPersonList);
        }

        // creating unique identifier for registration
        String transactionId = UUID.randomUUID().toString();

        List<Registration> registrations = savedPeopleToRegister.stream()
                .map(person -> Registration.builder()
                        .person(person)
                        .eventTerm(eventTerm)
                        .status(registrationStatus)
                        .transactionId(transactionId)
                        .build())
                .toList();

        List<Registration> savedRegistrations = new ArrayList<>();
        registrationRepository.saveAll(registrations).forEach(savedRegistrations::add);
        return savedRegistrations;
    }
}
