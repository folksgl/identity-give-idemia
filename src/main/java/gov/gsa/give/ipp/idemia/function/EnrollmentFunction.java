package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.IppLocation;
import gov.gsa.give.ipp.idemia.model.IppReqApplicant;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


@Component
public class EnrollmentFunction implements Function<Message<IppReqApplicant>, Message<String>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @Override
    public Message<String> apply(Message<IppReqApplicant> message) {
        IppReqApplicant applicant = message.getPayload();

        System.out.println("*****\nINSIDE ENROLLMENT FUNCTION\n");

        System.out.println("Headers:\n" + message.getHeaders());

        String result = preEnrollmentService.createIppApplicant(applicant);
        System.out.println(result);
        Message<String> response = MessageBuilder.withPayload("Success")
                .setHeader("contentType", "application/json").build();

        System.out.println("\n*****");

        return response;
    }
}
