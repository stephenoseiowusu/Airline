package DTO.models;

import lombok.Data;

@Data
public class AirlineUser {
    private String username;
    private String firstName;
    private String password;
    private String lastName;
    public AirlineUser(String username, String firstName,  String lastName,String password){
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
    }
    public String getUserName(){
        return this.username;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getPassword(){
        return this.password;
    }

}
