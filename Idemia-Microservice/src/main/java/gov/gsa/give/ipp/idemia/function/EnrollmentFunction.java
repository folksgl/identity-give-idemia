package gov.gsa.give.ipp.idemia.function;

import gov.gsa.give.ipp.idemia.model.request.IppApplicant;
import gov.gsa.give.ipp.idemia.model.response.GiveMessage;
import gov.gsa.give.ipp.idemia.model.response.IppError;
import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import gov.gsa.give.ipp.idemia.model.response.IppStatus;
import gov.gsa.give.ipp.idemia.service.MessageBuilderService;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

/**
 * POST /enrollment - Enrollment Spring Cloud Function that enrolls an individual with Idemia's IPP service.
 */
@Component
public class EnrollmentFunction implements Function<Message<IppApplicant>, Message<IppResponse>> {

    @Autowired
    PreEnrollmentService preEnrollmentService;
    @Autowired
    MessageBuilderService messageBuilderService;

    /**
     * Invocation of the EnrollmentFunction. Proxies request to Idemia UEP API for a UEID, which is used to check on individual's IPP event status.
     *
     * @param message - {@link IppApplicant} wrapped in a {@link Message} containing Idemia required information.
     * @return Result from Idemia UEP API.
     */
    @Override
    public Message<IppResponse> apply(Message<IppApplicant> message) {
        IppApplicant applicant = message.getPayload();

        // catch bad/non existent values in IppApplcantmessage
        UUID uuid;
        ArrayList<String> errorList = new ArrayList<>();
        try {
            uuid = UUID.fromString((String) applicant.getUuid());
        } catch (IllegalArgumentException | NullPointerException e) {
            errorList.add(GiveMessage.INVALID_UUID.value);
        }
        if (applicant.getFirstName() == null) {
            errorList.add(GiveMessage.INVALID_FIRST_NAME.value);
        }
        if (applicant.getLastName() == null) {
            errorList.add(GiveMessage.INVALID_LAST_NAME.value);
        }
        if (applicant.getEmailAddress() == null /* need to test if email is in correct format */) {
            errorList.add(GiveMessage.INVALID_EMAIL.value);
        }
        if (!errorList.isEmpty()) {
            IppResponse error = new IppError(errorList.toArray(new String[errorList.size()]));
            return messageBuilderService.buildMessagewithStatusCode(error, HttpStatus.BAD_REQUEST.value());
        }

        // Invoke service that makes request to Idemia API
        try {
            preEnrollmentService.createIppApplicant(applicant);
        } catch (Exception e) {
            // will become specific error depending on exception thrown in PreEnrollmentService
            IppResponse error = new IppError(new String[]{""});
            return messageBuilderService.buildMessagewithStatusCode(error, HttpStatus.BAD_REQUEST.value());
        }

        IppResponse ippResponse = new IppStatus(GiveMessage.USER_ENROLLED.value);

        return messageBuilderService.buildMessagewithStatusCode(ippResponse, HttpStatus.CREATED.value());
    }
}
