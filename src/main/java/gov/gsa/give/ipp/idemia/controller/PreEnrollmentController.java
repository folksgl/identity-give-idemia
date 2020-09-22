package gov.gsa.give.ipp.idemia.controller;

import gov.gsa.give.ipp.idemia.model.IppApplicantRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PreEnrollmentController {

    @GetMapping("/enrollment")
    public Mono<String> get() {
        return Mono.just("Hello World!");
    }
}
