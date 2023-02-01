package nus.iss.tfip.pafworkshop22.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSVP {

    private int id;
    private String name;
    private String email;
    private String phone;
    private Date confirmation_date;
    private String comments;

}
