package gov.gsa.give.ipp.idemia.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString()
public class Address {
    public String building;
    public String city;
    public String address;
    public String postalCode;
}
