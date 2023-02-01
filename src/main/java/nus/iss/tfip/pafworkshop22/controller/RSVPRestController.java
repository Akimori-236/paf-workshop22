package nus.iss.tfip.pafworkshop22.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.tfip.pafworkshop22.service.RSVPService;
import nus.iss.tfip.pafworkshop22.model.RSVP;

@RestController
@RequestMapping(path = "/api")
public class RSVPRestController {

    @Autowired
    private RSVPService rsvpSvc;

    @GetMapping(path = "/rsvps", produces = "application/json")
    public ResponseEntity<String> getAll() {
        String jsonStr = rsvpSvc.getAllRSVP();

        if (jsonStr == "" || jsonStr == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No RSVPs");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonStr);
    }

    @GetMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRsvpByName(@RequestParam(value = "q") String name) {
        String jsonStr = rsvpSvc.getRsvpByName(name);
        System.out.println("    JSON STRING >>> " + jsonStr);
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jsonStr);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body("No RSVP with name %s".formatted(name));
        }
    }

    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upsertRsvp(@RequestBody RSVP rsvp) {
        Boolean isInserted = rsvpSvc.upsertRsvp(rsvp);
        if (isInserted) {
            return new ResponseEntity<>("Entry created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid entry", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(path = "/rsvp/{email}")
    public ResponseEntity<String> updateRsvp(@PathVariable(value = "email") String email, @RequestBody RSVP rsvp) {
        Boolean isInserted = rsvpSvc.updateRsvp(rsvp);
        if (isInserted) {
            return new ResponseEntity<>("Entry updated", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(path = "/rsvps/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> countRsvp() {
        String jsonStr = rsvpSvc.countRsvp();

        if (jsonStr == "" || jsonStr == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No RSVPs");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonStr);
    }
}
