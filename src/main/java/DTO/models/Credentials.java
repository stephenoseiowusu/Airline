package DTO.models;

import lombok.Data;

@Data
public class Credentials {
    private String username;
    private String password;
    public Credentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
}
