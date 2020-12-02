package gov.gsa.give.ipp.idemia;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;
import org.springframework.cloud.function.context.catalog.FunctionInspector;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

public class LambdaHandler extends SpringBootRequestHandler<APIGatewayProxyRequestEvent,
        APIGatewayProxyResponseEvent> {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FunctionInspector inspector;

    @Autowired
    ApplicationContext ctx;

    public LambdaHandler(Class<?> configurationClass) {
        super(configurationClass);
    }

    public LambdaHandler() {
        super();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        initialize(context);
        Object input = convertEvent(event);
        Publisher<?> output = apply(extract(input));

        return result(input, output);
    }

    protected Object convertEvent(APIGatewayProxyRequestEvent event) {

        Object body = new Object();
        if (event.getBody() != null) {
            body = deserializeBody(event.getBody());
        }

        if (functionAcceptsMessage()) {
            return new GenericMessage<>(body, getHeaders(event));
        } else {
            return body;
        }
    }

    private boolean functionAcceptsMessage() {
        return inspector.isMessage(function());
    }

    private Object deserializeBody(String json) {
        try {
            return mapper.readValue(json, getInputType());
        } catch (Exception e) {
            throw new IllegalStateException("Cannot convert event", e);
        }
    }

    private MessageHeaders getHeaders(APIGatewayProxyRequestEvent event) {
        Map<String, Object> headers = new HashMap<>();
        if (event.getHeaders() != null) {
            headers.putAll(event.getHeaders());
        }
        if (event.getQueryStringParameters() != null) {
            headers.putAll(event.getQueryStringParameters());
        }
        if (event.getPathParameters() != null) {
            headers.putAll(event.getPathParameters());
        }
        headers.put("httpMethod", event.getHttpMethod());
        headers.put("request", event);
        return new MessageHeaders(headers);
    }

    @Override
    protected APIGatewayProxyResponseEvent convertOutput(Object output) {
        if (functionReturnsMessage(output)) {
            Message<?> message = (Message<?>) output;
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode((Integer) message.getHeaders().getOrDefault("statuscode", HttpStatus.OK.value()))
                    .withHeaders(toResponseHeaders(message.getHeaders())).withBody(serializeBody(message.getPayload()));
        } else {
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.OK.value())
                    .withBody(serializeBody(output));

        }
    }

    private Flux<?> extract(Object input) {
        if (input instanceof Collection) {
            return Flux.fromIterable((Iterable<?>) input);
        }
        return Flux.just(input);
    }

    private boolean functionReturnsMessage(Object output) {
        return output instanceof Message;
    }

    private Map<String, String> toResponseHeaders(MessageHeaders messageHeaders) {
        Map<String, String> responseHeaders = new HashMap<>();
        messageHeaders.forEach((key, value) -> responseHeaders.put(key, value.toString()));
        return responseHeaders;
    }

    private String serializeBody(Object body) {
        try {
            return mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot convert output", e);
        }
    }

}
