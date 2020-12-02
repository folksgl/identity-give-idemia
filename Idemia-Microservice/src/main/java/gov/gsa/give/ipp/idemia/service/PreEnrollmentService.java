package gov.gsa.give.ipp.idemia.service;

import gov.gsa.give.ipp.idemia.model.response.IppError;
import gov.gsa.give.ipp.idemia.model.response.IppLocation;
import gov.gsa.give.ipp.idemia.model.request.IppApplicant;
import gov.gsa.give.ipp.idemia.model.response.IppResponse;
import gov.gsa.give.ipp.idemia.model.response.IppStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PreEnrollmentService {
    @Value("${idemia.endpoint}")
    private String idemiaEndpoint;

    @Value("${idemia.request-timeout}")
    private Duration requestTimeout;

    @Value("${idemia.user-id}")
    private String apiKey;

    private WebClient client;

    public PreEnrollmentService() {}

    /**
     * Submits a request to create a new IPP applicant to the UEP API. If the applicant is created
     * successfully, a valid UEID is stored and a http 201 code is returned.
     * @param applicant the PII associated with the applicant.
     * @return the enrollment code associated with the applicant.
     */
    public void createIppApplicant(IppApplicant applicant) { }

    /**
     * Submits a request to the UEP API to search for ipp locations near a supplied address.
     * @param zipcode the address to run a search against.
     * @return a list of ipp locations near the given location with address, hours, and contact information.
     */
    public List<IppLocation> getIppLocationList(String zipcode) {

        return new ArrayList<IppLocation>();
    }

    /**
     * Updates the status of an enrolled individual with the status parameter.
     * @param ueid the Idemia assigned value for the individual.
     * @param status the new status for the individual.
     * @return updated status associated with individual.
     */
    public String updateProofingResults(String ueid, String status) {
        return "No status update available";
    }

    /**
     * Checks the status of an enrolled individual's ipp event.
     * @param uuid the {@link UUID} associated with the applicant.
     * @return the proofing result associated with the event.
     */
    public IppResponse getProofingResults(UUID uuid) {
        return new IppStatus("No status available");
    }
}
