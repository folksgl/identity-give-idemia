package gov.gsa.give.ipp.idemia.service;

import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageBuilderService {
    public MessageBuilderService() {}

    public Message<IppResponse> buildMessagewithStatusCode(IppResponse ippResponse, int code) {
        Message<IppResponse> response = MessageBuilder.withPayload(ippResponse)
                .setHeader("contentType", "application/json")
                .setHeader("statuscode", code).build();
        return response;
    }
}
