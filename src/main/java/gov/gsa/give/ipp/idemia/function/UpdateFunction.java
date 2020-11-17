package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.request.IppApplicant;
import gov.gsa.give.ipp.idemia.model.request.IppUpdate;
import gov.gsa.give.ipp.idemia.model.response.IppDefault;
import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * PUT /update - Status Update Spring Cloud Function that updates an individual's IPP event status based on a provided UEID.
 */
@Component
public class UpdateFunction implements Function<Message<IppUpdate>, Message<IppResponse>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    /**
     * Invocation of the UpdateFunction. Retrieves UEID from query parameter and updates status associated with UEID with the provided status.
     * @param message - {@link IppUpdate} wrapped in a {@link Message} containing an updated status.
     * @return Status of the status update.
     */
    @Override
    public Message<IppResponse> apply(Message<IppUpdate> message) {

        String ueid = (String) message.getHeaders().get("ueid");
        IppUpdate update = message.getPayload();
        String status = update.getIppstatus();

        String result = preEnrollmentService.updateProofingResults(ueid, status);
        IppResponse ippResponse = new IppDefault(result);

        Message<IppResponse> response = MessageBuilder.withPayload(ippResponse)
                .setHeader("contentType", "application/json").build();

        return response;
    }
}
