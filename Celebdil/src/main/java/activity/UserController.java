package main.java.activity;

import main.java.data.Address;
import main.java.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {

    @RequestMapping(method = GET, value = "/user")
    public User getUserData() {
        User user = new User();
        user.setTitle("Sir");
        user.setFirstName("Robin");
        user.setMiddleName("The");
        user.setSurName("Fearless");
        user.setSuffix("II");
        user.setAccountNumber(UUID.randomUUID());
        user.setAddress(new Address());
        return user;
    }
}
