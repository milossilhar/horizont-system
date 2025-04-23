package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventTermCapacityResponse {

    @JsonProperty("capacity")
    private final Integer capacity;

    @JsonProperty("confirmed")
    private Long confirmed;

    @JsonProperty("registered")
    private Long registered;

    @JsonProperty("available")
    public Long getAvailable() {
        return this.capacity - this.confirmed;
    }

    @JsonIgnore
    public EventTermCapacityResponse addConfirmed(Long confirmed) {
        if (this.confirmed == null) this.confirmed = 0L;
        this.confirmed += confirmed;
        return this;
    }

    @JsonIgnore
    public EventTermCapacityResponse addRegistered(Long registered) {
        if (this.registered == null) this.registered = 0L;
        this.registered += registered;
        return this;
    }
}
