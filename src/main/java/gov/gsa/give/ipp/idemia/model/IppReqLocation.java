package gov.gsa.give.ipp.idemia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class IppReqLocation extends IppRequest{
    public String zip;
}
