package main.java.activity;

import main.java.data.Address;
import main.java.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController {

    // Keys for allParams
    public static final String FIRST_NAME_KEY = "firstName";
    public static final String MIDDLE_NAME_KEY = "middleName";
    public static final String SUR_NAME_KEY = "surName";
    public static final String SUFFIX_KEY = "suffix";
    public static final String TITLE_KEY = "title";
    public static final String ADDRESS_KEY = "address";

    /**
     * This API will take a user (probably by UUID) and return information about the user
     * @return
     */
    @RequestMapping(method = GET, value = "/get-user")
    public @ResponseBody User getUserData(@RequestParam(value="name", defaultValue="User") String name) {
        User user = new User();
        user.setTitle("Sir");
        user.setFirstName(name);
        user.setMiddleName("The");
        user.setSurName("Fearless");
        user.setSuffix("II");
        user.setAccountNumber(UUID.randomUUID());
        user.setAddress(new Address());
        return user;
    }

    /**
     * This API will take a new user object and create the user (probably returning the UUID for the User)
     * @param allParams
     * @return
     */
    @RequestMapping(method = POST, value = "/create-user")
    public @ResponseBody User createNewUser(@RequestParam Map<String,String> allParams) {
        User user = new User();
        user.setTitle(allParams.get(TITLE_KEY));
        user.setFirstName(allParams.get(FIRST_NAME_KEY));
        user.setMiddleName(allParams.get(MIDDLE_NAME_KEY));
        user.setSurName(allParams.get(SUR_NAME_KEY));
        user.setSuffix(allParams.get(SUFFIX_KEY));
        user.setAccountNumber(UUID.randomUUID());
        user.setAddress(null);
        return user;
    }
}
