package DTO.models;

import lombok.Data;

@Data
public class AirlineUser {
    private String username;
    private String firstName;
    private String password;
    private String lastName;
    public int userId;
    public AirlineUser(int userId,String username, String firstName,  String lastName, String password){
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.userId = userId;
    }


}
