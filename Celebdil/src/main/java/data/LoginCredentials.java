package main.java.data;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginCredentials {

    private String username;
    private String password;
    private UUID accountId;

}
