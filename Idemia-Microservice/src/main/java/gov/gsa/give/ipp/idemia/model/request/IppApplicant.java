package gov.gsa.give.ipp.idemia.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class IppApplicant extends IppRequest {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String emailAddress;
}
