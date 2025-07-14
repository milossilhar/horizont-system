package sk.leziemevpezinku.spring.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    EVENT(false), // random event (aj camp)
    CAMP(true), // denny tabor
    SCHOOL_CLIMB(true),
    ECA(true);

    private final boolean generateLessons;
}
