package nus.iss.tfip.pafworkshop22.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop22.model.RSVP;

@Repository
public class RSVPRepository {

    @Autowired
    JdbcTemplate template;

    String getAllSQL = "SELECT * FROM rsvp";
    String getByNameSQL = "SELECT * From rsvp WHERE name LIKE '%' ? '%'";
    String upsertSQL = """
            INSERT INTO rsvp (name, email, phone, confirmation_date, comments)
            VALUES (?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
            name=VALUES(name),
            email=VALUES(email),
            phone=VALUES(phone),
            confirmation_date=VALUES(confirmation_date),
            comments=VALUES(comments);
                            """;;
    String updateSQL = "UPDATE rsvp SET name=?, email=?, phone=?, confirmation_date=?, comments=? WHERE id=?";
    String countSQL = "SELECT COUNT(*) FROM rsvp";

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

    public Boolean upsertRsvp(RSVP rsvp) {
        Boolean saved = false;
        // execute SQL
        saved = template.execute(upsertSQL, (PreparedStatementCallback<Boolean>) ps -> {
            // inject variables into the SQL statement
            ps.setInt(1, rsvp.getId());
            ps.setString(2, rsvp.getName());
            ps.setString(3, rsvp.getEmail());
            ps.setString(4, rsvp.getPhone());
            ps.setDate(5, rsvp.getConfirmation_date());
            ps.setString(6, rsvp.getComments());
            Boolean result = ps.execute();
            return result;
        });
        return saved;
    }

    public Boolean updateRsvp(RSVP rsvp) {
        Boolean saved = false;
        // execute SQL
        saved = template.execute(updateSQL, (PreparedStatementCallback<Boolean>) ps -> {
            // inject variables into the SQL statement
            ps.setInt(6, rsvp.getId());
            ps.setString(1, rsvp.getName());
            ps.setString(2, rsvp.getEmail());
            ps.setString(3, rsvp.getPhone());
            ps.setDate(4, rsvp.getConfirmation_date());
            ps.setString(5, rsvp.getComments());
            Boolean result = ps.execute();
            return result;
        });
        return saved;
    }

    public Integer countRsvp() {
        Integer count = 0;
        count = template.queryForObject(countSQL, Integer.class);
        if (count == null) {
            return 0;
        } else {
            return count;
        }
    }
}
