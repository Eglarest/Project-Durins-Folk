package main.java.controller;

import main.java.data.LoginCredentials;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidLoginException;
import main.java.exception.InvalidParameterException;
import main.java.service.CredentialService;
import main.java.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static main.java.controller.ControllerConstants.PASSWORD_KEY;
import static main.java.controller.ControllerConstants.USERNAME_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class LoginController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private ValidationService validationService;

    /**
     * This API is for validating user login requests. It takes a username/password combo and
     * returns a boolean (true if valid)
     * @param allParams
     * @return
     */
    @RequestMapping(method = POST, value = "/login")
    public @ResponseBody String createNewUser(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException, InvalidLoginException {
        String username = allParams.get(USERNAME_KEY);
        String password = allParams.get(PASSWORD_KEY);

        validationService.isNotNullOrEmpty(username, USERNAME_KEY);
        validationService.isNotNullOrEmpty(password, PASSWORD_KEY);

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUsername(username);
        loginCredentials.setPassword(password);

        loginCredentials = credentialService.loginUser(loginCredentials);
        return loginCredentials.getAccountId().toString();
    }
}
