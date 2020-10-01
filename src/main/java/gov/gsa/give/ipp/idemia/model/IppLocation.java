package gov.gsa.give.ipp.idemia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString()
public class IppLocation {
    public String name;
    public String phoneNumber; // Will be changed to some other object
    public String timeZone;
    public Address address;
    public String details;
    public Geocode geocode;
    public String hours;
}
