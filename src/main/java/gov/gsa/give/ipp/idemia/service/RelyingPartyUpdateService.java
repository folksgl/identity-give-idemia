package gov.gsa.give.ipp.idemia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.time.Duration;

public class RelyingPartyUpdateService {
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
}
