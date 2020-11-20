package gov.gsa.give.ipp.idemia.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class IppDefault extends IppResponse{
    private String message;
}