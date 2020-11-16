package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.IppReqApplicant;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class EnrollmentFunction implements Consumer<Message<IppReqApplicant>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @Override
    public void accept(Message<IppReqApplicant> message) {
        IppReqApplicant applicant = message.getPayload();
        String result = preEnrollmentService.createIppApplicant(applicant);
        System.out.println(result);
    }
}
