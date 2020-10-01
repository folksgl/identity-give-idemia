package gov.gsa.give.ipp.idemia.controller;

import gov.gsa.give.ipp.idemia.model.IppLocation;
import gov.gsa.give.ipp.idemia.model.IppReqApplicant;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

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
    public Mono<List<IppLocation>> locations(@RequestParam("zip") String zipcode) {
        return preEnrollmentService.getIppLocationList(zipcode);
    }

    @GetMapping("/update")
    public Mono<String> update(@RequestParam("uuid") UUID uuid) {
        return preEnrollmentService.getProofingResults(uuid);
    }

    @PutMapping("/update")
    public Mono<String> update(@RequestBody String request) {
        return null;
    }
}
