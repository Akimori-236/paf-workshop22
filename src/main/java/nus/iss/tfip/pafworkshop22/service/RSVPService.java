package nus.iss.tfip.pafworkshop22.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

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
            Json.createObjectBuilder()
                .add("name", r.getName())
                .add("email", r.getEmail())
                .add("phone", r.getPhone())
                .add("confirmation_date", r.getConfirmation_date().toString())
                .add("comments", r.getComments())
                .build();
            
            }
        return null;
    }
    // public JsonObjectBuilder toJson() {
    //     JsonArrayBuilder jab = Json.createArrayBuilder();
    //     for (Type t : this.types) {
    //         jab.add(Json.createObjectBuilder()
    //                 .add("type", t.getType())
    //                 .add("count", t.getCount()));
    //     }
    //     return Json.createObjectBuilder()
    //             .add("totalCount", this.getTotal_count())
    //             .add("types", jab);
    // }
}
