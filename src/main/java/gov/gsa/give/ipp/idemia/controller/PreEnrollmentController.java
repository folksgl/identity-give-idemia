package gov.gsa.give.ipp.idemia.controller;

import gov.gsa.give.ipp.idemia.model.IppLocation;
import gov.gsa.give.ipp.idemia.model.IppReqApplicant;
import gov.gsa.give.ipp.idemia.service.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class PreEnrollmentController {

    @Autowired
    PreEnrollmentService preEnrollmentService;

    @PostMapping("/enrollment")
    public ResponseEntity<String> enrollment(@RequestBody IppReqApplicant applicant) {
        String response = preEnrollmentService.createIppApplicant(applicant);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<IppLocation>> locations(@RequestParam("zip") String zipcode) {
        ArrayList<IppLocation> locationList = (ArrayList<IppLocation>) preEnrollmentService.getIppLocationList(zipcode);
        return ResponseEntity.ok(locationList);
    }

    @GetMapping("/update")
    public ResponseEntity<String> update(@RequestParam("uuid") UUID uuid) {
        String response = preEnrollmentService.getProofingResults(uuid);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody String request) {
        return null;
    }
}
