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
    String insertSQL = "INSERT INTO rsvp(name, email, phone, confirmation_date, comments) VALUES(?, ?, ?, ?, ?)";
    String updateSQL = "UPDATE rsvp SET name=?, email=?, phone=?, confirmation_date=?, comments=? WHERE id=?";
    String countSQL;

    public List<RSVP> getAllRsvp() {
        List<RSVP> rsvpList = new LinkedList<>();
        rsvpList = template.query(getAllSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
        return rsvpList;
    }

    public List<RSVP> getByName(String name) {
        List<RSVP> rsvpList = new LinkedList<>();
        rsvpList = template.query(getByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class), name);
        return rsvpList;
    }

}
