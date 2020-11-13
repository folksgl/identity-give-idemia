package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class UpdateFunction implements Consumer<Message<String>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @Override
    public void accept(Message<String> message) {

        String ueid = "";
        String status = message.getPayload();

        preEnrollmentService.updateProofingResults(ueid, status);

        System.out.println("user status update");
    }
}
