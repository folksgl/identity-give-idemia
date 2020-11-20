package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.request.IppApplicant;
import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import gov.gsa.give.ipp.idemia.model.response.IppStatus;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * POST /enrollment - Enrollment Spring Cloud Function that enrolls an individual with Idemia's IPP service.
 */
@Component
public class EnrollmentFunction implements Function<Message<IppApplicant>, Message<IppResponse>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    /**
     * Invocation of the EnrollmentFunction. Proxies request to Idemia UEP API for a UEID, which is used to check on individual's IPP event status.
     *
     * @param message - {@link IppApplicant} wrapped in a {@link Message} containing Idemia required information.
     * @return Result from Idemia UEP API.
     */
    @Override
    public Message<IppResponse> apply(Message<IppApplicant> message) {
        IppApplicant applicant = message.getPayload();

        String result = preEnrollmentService.createIppApplicant(applicant);
        IppResponse ippResponse = new IppStatus(result);

        Message<IppResponse> response = MessageBuilder.withPayload(ippResponse)
                .setHeader("contentType", "application/json").build();

        return response;
    }
}
