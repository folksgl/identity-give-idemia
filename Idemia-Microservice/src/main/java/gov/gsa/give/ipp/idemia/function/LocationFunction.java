package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.response.IppLocation;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LocationFunction implements Function<Message<Void>, Message<List<IppLocation>>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    /**
     * Invocation of the LocationFunction. Extracts ZIP from query parameter and retrieves IPP locations from the PreEnrollmentService.
     *
     * @param message - empty message, used to extract query parameters (the ZIP code)
     * @return List of {@link IppLocation} objects.
     */
    @Override
    public Message<List<IppLocation>> apply(Message<Void> message) {

        MessageHeaders metaInfo = message.getHeaders();
        String zip = (String) metaInfo.get("zip");

        List<IppLocation> locations = preEnrollmentService.getIppLocationList(zip);

        Message<List<IppLocation>> response = MessageBuilder.withPayload(locations)
                .setHeader("contentType", "application/json").build();
        return response;
    }
}
