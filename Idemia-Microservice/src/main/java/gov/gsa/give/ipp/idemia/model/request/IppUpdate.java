package gov.gsa.give.ipp.idemia.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class IppUpdate extends IppRequest {
    private String ippstatus;
}
