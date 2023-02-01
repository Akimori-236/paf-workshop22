package nus.iss.tfip.pafworkshop22.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop22.model.RSVP;

@Repository
public class RSVPRepository {

    @Autowired
    JdbcTemplate template;

    String getAllSQL = "SELECT * FROM rsvp";
    String getByNameSQL = "SELECT * From rsvp WHERE name=?";

    public List<RSVP> getAllRsvp() {
        List<RSVP> rsvpList = new LinkedList<>();
        rsvpList = template.query("SELECT * FROM rsvp", BeanPropertyRowMapper.newInstance(RSVP.class));
        return rsvpList;
    }

    public RSVP getByName(String name) {
        RSVP rsvp = template.queryForObject(getByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class), name);
        return rsvp;
    }

}
