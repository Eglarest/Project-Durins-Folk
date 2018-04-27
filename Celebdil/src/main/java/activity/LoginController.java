package main.java.activity;

import main.java.data.LoginCredentials;
import main.java.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class LoginController {

    // Keys for allParams
    public final String USERNAME_KEY = "username";
    public final String PASSWORD_KEY = "password";

    @Autowired
    CredentialService credentialService;

    /**
     * This API is for validating user login requests. It takes a username/password combo and
     * returns a boolean (true if valid)
     * @param allParams
     * @return
     */
    @RequestMapping(method = POST, value = "/login")
    public @ResponseBody boolean createNewUser(@RequestParam Map<String,String> allParams) {
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUsername(allParams.get(USERNAME_KEY));
        loginCredentials.setPassword(allParams.get(PASSWORD_KEY));
        return credentialService.isValidLogin(loginCredentials);
    }
}
