package gov.gsa.give.ipp.idemia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString()
public class Geocode {
    public String latitude;
    public String longitude;
}
