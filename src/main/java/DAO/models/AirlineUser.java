package DAO.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AirlineUser {
    private int id;
    private String first_name;
    private String last_name;
    private String password;
    private String login_id;
    public AirlineUser(){

    }
    public AirlineUser(String first_name, String last_name, String password, String login_id){
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.login_id = login_id;
    }

}
