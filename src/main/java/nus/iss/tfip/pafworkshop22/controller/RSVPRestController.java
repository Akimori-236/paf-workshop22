package nus.iss.tfip.pafworkshop22.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.tfip.pafworkshop22.service.RSVPService;

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

    @GetMapping(path = "/rsvp", produces = "application/json")
    public ResponseEntity<String> getRsvpByName(@RequestParam(value="q") String name) {
        String jsonStr = rsvpSvc.getRsvpByName(name);
        System.out.println("    JSON STRING >>> " + jsonStr);
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jsonStr);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body("No RSVP with name %s".formatted(name));
        }
    }


}
