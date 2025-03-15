package sk.leziemevpezinku.spring.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    EVENT(false),
    CAMP(true),
    SCHOOL_CLIMB(true),
    ECA(true);

    private final boolean generateLessons;
}
