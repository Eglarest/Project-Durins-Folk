package main.java.controller;

import main.java.data.Contact.ContactStatus;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidParameterException;
import main.java.service.ContactService;
import main.java.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

import static main.java.controller.ControllerConstants.RECEIVING_USER_KEY;
import static main.java.controller.ControllerConstants.SETTING_USER_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ContactController {

    @Autowired
    ValidationService validationService;

    @Autowired
    ContactService contactService;

    /**
     * @return
     */
    @RequestMapping(method = POST, value = "/create-contact")
    public @ResponseBody int createContact (@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String settingUser = allParams.get(SETTING_USER_KEY);
        String receivingUSer = allParams.get(RECEIVING_USER_KEY);

        validationService.validateUUID(settingUser, SETTING_USER_KEY);
        validationService.validateUUID(receivingUSer, RECEIVING_USER_KEY);

        return contactService.addContact(UUID.fromString(settingUser), UUID.fromString(receivingUSer), ContactStatus.PENDING_HIGHER);
    }
}
