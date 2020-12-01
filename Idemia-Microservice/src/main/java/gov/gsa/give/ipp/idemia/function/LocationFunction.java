package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.response.*;
import gov.gsa.give.ipp.idemia.service.MessageBuilderService;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;


/**
 * GET /locations - Location Spring Cloud Function that finds IPP locations based a provided ZIP code.
 */
@Component
public class LocationFunction implements Function<Message<Void>, Message<IppResponse>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;
    @Autowired
    MessageBuilderService messageBuilderService;

    /**
     * Invocation of the LocationFunction. Extracts ZIP from query parameter and retrieves IPP locations from the PreEnrollmentService.
     *
     * @param message - empty message, used to extract query parameters (the ZIP code)
     * @return List of {@link IppLocation} objects.
     */
    @Override
    public Message<IppResponse> apply(Message<Void> message) {

        MessageHeaders metaInfo = message.getHeaders();
        String zip = (String) metaInfo.get("zip");
        String regex = "\\d{5}(-\\d{4})?";

        // catch bad/non existent zip codes before making a request to the Idemia API.
        if (zip == null || !zip.matches(regex)) {
            IppResponse ippError = new IppError(ErrorMessage.INVALID_ZIP.value);
            Message<IppResponse> response = messageBuilderService.buildMessagewithStatusCode(ippError, HttpStatus.BAD_REQUEST.value());
            return response;
        }

        List<IppLocation> locations = preEnrollmentService.getIppLocationList(zip);
        IppResponse ippResponse = new IppLocationList(locations);

        Message<IppResponse> response = messageBuilderService.buildMessagewithStatusCode(ippResponse, HttpStatus.OK.value());
        return response;
    }
}
