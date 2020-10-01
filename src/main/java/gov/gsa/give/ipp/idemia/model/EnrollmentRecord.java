package gov.gsa.give.ipp.idemia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EnrollmentRecord {
    private String UUID;
    private String Status;
    private String UEID;
    private Timestamp lastUpdated;
}
