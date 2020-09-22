package gov.gsa.give.ipp.idemia.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class IppApplicantRequest {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String emailAddress;
    @JsonProperty("IPPVersion")
    private String ippVersion;

    public IppApplicantRequest() {
        uuid = UUID.randomUUID();
        ippVersion = "1.5";
    }
}
