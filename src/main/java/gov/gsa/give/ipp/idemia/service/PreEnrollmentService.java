package gov.gsa.give.ipp.idemia.service;

import gov.gsa.give.ipp.idemia.model.IppLocation;
import gov.gsa.give.ipp.idemia.model.IppReqApplicant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        client = WebClient
                .builder()
                .baseUrl(idemiaEndpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                                .enableLoggingRequestDetails(true))
                        .build())
                .build();
    }

    /**
     * Submits a request to create a new IPP applicant to the UEP API. If the applicant is created
     * successfully, a valid UEID is stored and a http 201 code is returned.
     * @param applicant the PII associated with the applicant.
     * @return the enrollment code associated with the applicant.
     */
    public Mono<String> createIppApplicant(IppReqApplicant applicant) {
        //applicant.setUp();
        return Mono.just(applicant.toString());
    }

    /**
     * Submits a request to the UEP API to search for ipp locations near a supplied address.
     * @param zipcode the address to run a search against.
     * @return a list of ipp locations near the given location with address, hours, and contact information.
     */
    public Mono<List<IppLocation>> getIppLocationList(String zipcode) {

        // Use this snippet as a guide.
        // return client
        // .post()
        // .body(Mono.just(regInfo), IppApplicantRequest.class)
        // .retrieve()
        // .onStatus(HttpStatus::isError, ClientResponse::createException)
        // .bodyToMono(UspsScore.class);

        return Mono.just(new ArrayList<IppLocation>());
    }

    /**
     * Checks the status of an enrolled individual's ipp event.
     * @param uuid the {@link UUID} associated with the applicant.
     * @return the proofing result associated with the event.
     */
    public Mono<String> getProofingResults(UUID uuid) {
        return Mono.just(uuid.toString());
    }
}
