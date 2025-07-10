package sk.leziemevpezinku.spring.api.enumeration;

/**
 * Represents the possible options for event condition types.
 * This enumeration defines the various types or constraints
 * that can be applied to an event's conditions. These types
 * are often used to specify validations or rules for events.
 *
 * Enumeration values:
 * - VALUE: Represents a specific value-based condition.
 * - DATE_MIN: Represents a condition where a minimum date is required.
 * - DATE_MAX: Represents a condition where a maximum date is required.
 * - DATE_RANGE: Represents a condition that specifies a valid range of dates.
 * - NUM_RANGE: Represents a condition that specifies a valid numerical range.
 * - NUM_MIN: Represents a condition where a minimum numerical value is required.
 * - NUM_MAX: Represents a condition where a maximum numerical value is required.
 */
public enum EventConditionTypeOption {
    VALUE,
    DATE_MIN,
    DATE_MAX,
    DATE_RANGE,
    NUM_RANGE,
    NUM_MIN,
    NUM_MAX
}
