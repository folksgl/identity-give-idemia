package gov.gsa.give.ipp.idemia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class IppReqApplicant extends IppRequest{
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String emailAddress;
}
