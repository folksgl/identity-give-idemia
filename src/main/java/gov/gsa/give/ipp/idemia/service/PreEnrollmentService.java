package gov.gsa.give.ipp.idemia.service;

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
}
