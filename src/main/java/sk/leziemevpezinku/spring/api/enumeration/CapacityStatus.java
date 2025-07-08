package sk.leziemevpezinku.spring.api.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CapacityStatus {
    FILLED(null),
    LAST_ONE(null),
    ALMOST_FILLED(20L),
    FILLING(45L),
    FREE(null);

    /**
     * Upper limit on the percentage of remaining capacity to be available for this status to activate.
     * E.g.:
     *  20 = max. 20% is available of capacity
     */
    private final Long percentThreshold;
}
