package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.IppLocation;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class LocationFunction implements Function<Message<Void>, Message<List<IppLocation>>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @Override
    public Message<List<IppLocation>> apply(Message<Void> m) {

        String zip = "";
        List<IppLocation> locations = preEnrollmentService.getIppLocationList(zip);

        Message<List<IppLocation>> message = MessageBuilder.withPayload(locations)
                .setHeader("contentType", "application/json").build();
        return message;
    }
}
