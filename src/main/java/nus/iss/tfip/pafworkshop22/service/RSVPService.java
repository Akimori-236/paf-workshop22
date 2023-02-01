package nus.iss.tfip.pafworkshop22.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import nus.iss.tfip.pafworkshop22.model.RSVP;
import nus.iss.tfip.pafworkshop22.repository.RSVPRepository;

@Service
public class RSVPService {

    @Autowired
    RSVPRepository rsvpRepo;

    public String getAllRSVP() {
        List<RSVP> rsvpList = rsvpRepo.getAllRsvp();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (RSVP r : rsvpList) {
            jab.add(Json.createObjectBuilder()
                    // .add("id", r.getId())
                    .add("name", r.getName())
                    .add("email", r.getEmail())
                    .add("phone", r.getPhone())
                    .add("confirmation_date", r.getConfirmation_date().toString())
                    .add("comments", "%s".formatted(r.getComments())));
        }
        return jab.build().toString();
    }

    public String getRsvpByName(String name) {
        List<RSVP> rsvpList = rsvpRepo.getByName(name);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (RSVP r : rsvpList) {
            jab.add(Json.createObjectBuilder()
                    // .add("id", r.getId())
                    .add("name", r.getName())
                    .add("email", r.getEmail())
                    .add("phone", r.getPhone())
                    .add("confirmation_date", r.getConfirmation_date().toString())
                    .add("comments", "%s".formatted(r.getComments())));
        }
        return jab.build().toString();
    }

    public Boolean upsertRsvp(RSVP rsvp) {
        return rsvpRepo.upsertRsvp(rsvp);
    }

    public Boolean updateRsvp(RSVP rsvp) {
        return rsvpRepo.updateRsvp(rsvp);
    }

    public String countRsvp() {
        Integer count = rsvpRepo.countRsvp();
        return Json.createObjectBuilder()
                .add("no_of_rsvps", count)
                .build()
                .toString();
    }
}
