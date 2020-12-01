package gov.gsa.give.ipp.idemia.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString()
public class IppLocationList extends IppResponse {
    public List<IppLocation> locationList;
}
