package sk.leziemevpezinku.spring.api.enumeration;

/**
 * Represents the different types of repetition that an event term can follow.
 * These repetition types are used to specify the frequency of an event occurrence.
 * <p></p>
 * Enumeration values:
 * - ONCE: Indicates that the event occurs only one time. Required attributes are startDate, startTime, durationInMinutes
 * - DAILY: Indicates that the event repeats every day from startDate to endDate. Required attributes are startDate, startTime, endDate, durationInMinutes
 * - WEEKLY: Indicates that the event repeats weekly from startDate to endDate. Required attributes are startDate, startTime, endDate, durationInMinutes, dayOfWeek
 */
public enum EventTermRepeatType {
    ONCE,
    DAILY,
    WEEKLY
}
