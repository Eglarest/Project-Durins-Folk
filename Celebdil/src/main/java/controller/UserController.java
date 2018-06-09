package main.java.controller;

import com.google.common.base.Strings;
import main.java.data.Address;
import main.java.data.User;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidParameterException;
import main.java.service.AddressService;
import main.java.service.UserService;
import main.java.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static main.java.controller.ControllerConstants.ACCOUNT_NUMBER_KEY;
import static main.java.controller.ControllerConstants.FIRST_NAME_KEY;
import static main.java.controller.ControllerConstants.MIDDLE_NAME_KEY;
import static main.java.controller.ControllerConstants.SUFFIX_KEY;
import static main.java.controller.ControllerConstants.SURNAME_KEY;
import static main.java.controller.ControllerConstants.TITLE_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AddressService addressService;

    /**
     * This API will take a user (probably by UUID) and return information about the user
     * @return
     */
    @RequestMapping(method = POST, value = "/get-user")
    public @ResponseBody User getUserData(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String accountNumber = allParams.get(ACCOUNT_NUMBER_KEY);
        validationService.validateUUID(accountNumber, ACCOUNT_NUMBER_KEY);
        return userService.getUserByAccountNumber(UUID.fromString(accountNumber));
    }

    /**
     * This API will take a String and return user's with the string in parts of their profile.
     */
    @RequestMapping(method = GET, value = "/find-users")
    public @ResponseBody List<User> findUsersContaining(@RequestParam(value="search", defaultValue="") String string) throws InternalFailureException {
        if (Strings.isNullOrEmpty(string)) {
            return new ArrayList<>();
        }
        return userService.findUsersByString(string);
    }

    /**
     * This API will take a new user object and create the user (probably returning the UUID for the User)
     * @param allParams
     * @return
     */
    @RequestMapping(method = POST, value = "/create-user")
    public @ResponseBody User createNewUser(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String firstName = allParams.get(FIRST_NAME_KEY);
        String surName = allParams.get(SURNAME_KEY);

        Address address = addressService.extractAddress(allParams);

        validationService.isNotNullOrEmpty(firstName, FIRST_NAME_KEY);
        validationService.isNotNullOrEmpty(surName, SURNAME_KEY);

        User user = new User();
        user.setJoinDate(new Date());
        user.setTitle(allParams.get(TITLE_KEY));
        user.setFirstName(firstName);
        user.setMiddleName(allParams.get(MIDDLE_NAME_KEY));
        user.setSurname(surName);
        user.setSuffix(allParams.get(SUFFIX_KEY));
        user.setAddress(address);
        return userService.createNewUser(user);
    }
}
