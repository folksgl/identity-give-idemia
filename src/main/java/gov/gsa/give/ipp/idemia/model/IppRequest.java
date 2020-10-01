package gov.gsa.give.ipp.idemia.model;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class IppRequest {
    @JsonProperty("IPPVersion")
    private String ippVersion;
    private Timestamp timestamp;
    public IppRequest() {
        ippVersion = "1.5";
        long time = new Date().getTime();
        timestamp = new Timestamp(time);
    }
}
