package gov.gsa.give.ipp.idemia.controller;

import gov.gsa.give.ipp.idemia.model.IppApplicantRequest;
import gov.gsa.give.ipp.idemia.service.IdemiaUpdateService;
import gov.gsa.give.ipp.idemia.service.LocationService;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import gov.gsa.give.ipp.idemia.service.RelyingPartyUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class PreEnrollmentController {

    @Autowired
    PreEnrollmentService preEnrollmentService;
    LocationService locationService;
    IdemiaUpdateService idemiaUpdateService;
    RelyingPartyUpdateService relyingPartyUpdateService;

    @GetMapping("/")
    public Mono<String> helloWorld() {
        return Mono.just("Hello World!");
    }

    @PostMapping("/enrollment")
    public Mono<String> enrollment(@RequestBody IppApplicantRequest request) {
        return null;
    }

    @GetMapping("/locations")
    public Mono<String> locations() {
        return null;
    }

    @GetMapping("/update")
    public Mono<String> update() {
        return null;
    }

    @PutMapping("/update")
    public Mono<String> update(@RequestBody String request) {
        return null;
    }
}
