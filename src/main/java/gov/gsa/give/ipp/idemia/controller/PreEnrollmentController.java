package gov.gsa.give.ipp.idemia.controller;

import gov.gsa.give.ipp.idemia.model.IppReqApplicant;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class PreEnrollmentController {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @GetMapping("/")
    public Mono<String> helloWorld() {
        return Mono.just("Hello World!");
    }

    @PostMapping("/enrollment")
    public Mono<String> enrollment(@RequestBody IppReqApplicant applicant) {
        return preEnrollmentService.createIppApplicant(applicant);
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
