package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.IppLocation;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
public class StatusFunction implements Function<Message<Void>, Message<String>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @Override
    public Message<String> apply(Message<Void> voidMessage) {

        UUID uuid = UUID.randomUUID();

        String status = preEnrollmentService.getProofingResults(uuid);

        Message<String> message = MessageBuilder.withPayload(status)
                .setHeader("contentType", "application/json").build();
        return message;
    }
}
