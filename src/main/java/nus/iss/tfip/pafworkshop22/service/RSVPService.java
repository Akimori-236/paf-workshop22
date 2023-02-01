package nus.iss.tfip.pafworkshop22.service;

import java.sql.Date;
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

    public Integer upsertRsvp(String name, String email, String phone, Date confirmation_date, String comments) {
        return rsvpRepo.upsertRsvp(name, email, phone, confirmation_date, comments);
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
