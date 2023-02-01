package nus.iss.tfip.pafworkshop22.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nus.iss.tfip.pafworkshop22.model.RSVP;

@Repository
public class RSVPRepository {

    @Autowired
    JdbcTemplate template;

    String getAllSQL = "SELECT * FROM rsvp";
    String getByNameSQL = "SELECT * From rsvp WHERE name LIKE '%' ? '%'";
    String upsertSQL = """
            INSERT INTO rsvp (name, email, phone, confirmation_date, comments)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
            name=VALUES(name),
            phone=VALUES(phone),
            confirmation_date=VALUES(confirmation_date),
            comments=VALUES(comments);
                                """;
    String updateSQL = "UPDATE rsvp SET name=?, phone=?, confirmation_date=?, comments=? WHERE email=?";
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

    public Integer upsertRsvp(String name, String email, String phone, Date confirmation_date, String comments) {
       Integer rows = 0;
        // execute SQL
        rows = template.execute(upsertSQL, (PreparedStatementCallback<Integer>) ps -> {
            // inject variables into the SQL statement
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setDate(4, confirmation_date);
            ps.setString(5, comments);
            int result = ps.executeUpdate();
            return result;
        });
        return rows;
    }

    public Boolean updateRsvp(RSVP rsvp) {
        Boolean saved = false;
        // execute SQL
        saved = template.execute(updateSQL, (PreparedStatementCallback<Boolean>) ps -> {
            // inject variables into the SQL statement
            ps.setString(1, rsvp.getName());
            ps.setString(5, rsvp.getEmail());
            ps.setString(2, rsvp.getPhone());
            ps.setDate(3, rsvp.getConfirmation_date());
            ps.setString(4, rsvp.getComments());
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

    @Transactional
    public int[] batchInsert(List<RSVP> rsvp) {
        return template.batchUpdate(upsertSQL, (BatchPreparedStatementSetter) new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // inject variables into the SQL statement
                ps.setString(1, rsvp.get(i).getName());
                ps.setString(5, rsvp.get(i).getEmail());
                ps.setString(2, rsvp.get(i).getPhone());
                ps.setDate(3, rsvp.get(i).getConfirmation_date());
                ps.setString(4, rsvp.get(i).getComments());
            }

            public int getBatchSize() {
                return rsvp.size();
            }
        });
    }
}
