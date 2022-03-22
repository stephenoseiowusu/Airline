package DAO.models;

import org.hibernate.annotations.Table;

public class AirlineAdmin {
    private String login_id;
    private String password;
    private String first_name;
    private String last_name;
    public AirlineAdmin(String login_id, String password, String first_name, String last_name){
        this.login_id = login_id;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
