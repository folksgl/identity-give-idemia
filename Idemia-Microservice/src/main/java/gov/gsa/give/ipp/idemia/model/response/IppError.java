package gov.gsa.give.ipp.idemia.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class IppError extends IppResponse {
    private String[] error;
}
