package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import gov.gsa.give.ipp.idemia.model.response.IppStatus;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

/**
 * GET /update - Status Spring Cloud Function that gets the status of an individual associated with the given UUID.
 */
@Component
public class StatusFunction implements Function<Message<Void>, Message<IppResponse>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    /**
     * Invocation of the StatusFunction. Gets the status of an individual associated with the given UUID passed as a query parameter.
     * @param message - empty message, used to extract query parameter (UUID).
     * @return {@link IppResponse} containing the individual's status.
     */
    @Override
    public Message<IppResponse> apply(Message<Void> message) {

        MessageHeaders metaInfo = message.getHeaders();
        UUID uuid = UUID.fromString((String) metaInfo.get("uuid"));

        System.out.println(uuid);

        String status = preEnrollmentService.getProofingResults(uuid);
        IppResponse ippResponse = new IppStatus(status);

        Message<IppResponse> response = MessageBuilder.withPayload(ippResponse)
                .setHeader("contentType", "application/json").build();
        return response;
    }
}
