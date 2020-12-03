package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.response.GiveMessage;
import gov.gsa.give.ipp.idemia.model.response.IppError;
import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import gov.gsa.give.ipp.idemia.service.MessageBuilderService;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

/**
 * GET /enrollment - Status Spring Cloud Function that gets the status of an individual associated with the given UUID.
 */
@Component
public class StatusFunction implements Function<Message<Void>, Message<IppResponse>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;
    @Autowired
    MessageBuilderService messageBuilderService;

    /**
     * Invocation of the StatusFunction. Gets the status of an individual associated with the given UUID passed as a query parameter.
     * @param message - empty message, used to extract query parameter (UUID).
     * @return {@link IppResponse} containing the individual's status.
     */
    @Override
    public Message<IppResponse> apply(Message<Void> message) {

        UUID uuid;
        // catch bad/non existent UUIDs
        try {
            MessageHeaders metaInfo = message.getHeaders();
            uuid = UUID.fromString((String) metaInfo.get("uuid"));
        } catch (IllegalArgumentException | NullPointerException e) {
            IppResponse ippResponse = new IppError(new String[]{GiveMessage.INVALID_UUID.value});
            return messageBuilderService.buildMessagewithStatusCode(ippResponse, HttpStatus.BAD_REQUEST.value());
        }

        IppResponse ippResponse;
        try {
            ippResponse = preEnrollmentService.getProofingResults(uuid);
        } catch (Exception e) {
            // will become specific error depending on exception thrown in PreEnrollmentService
            IppResponse error = new IppError(new String[]{""});
            return messageBuilderService.buildMessagewithStatusCode(error, HttpStatus.BAD_REQUEST.value());
        }

        return messageBuilderService.buildMessagewithStatusCode(ippResponse, HttpStatus.OK.value());
    }
}
