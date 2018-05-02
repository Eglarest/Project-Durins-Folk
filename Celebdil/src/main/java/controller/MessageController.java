package main.java.controller;

import main.java.data.Message;
import main.java.exception.InvalidParameterException;
import main.java.service.MessageService;
import main.java.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static main.java.controller.ControllerConstants.ACCOUNT_NUMBER_KEY;
import static main.java.controller.ControllerConstants.MESSAGE_CONTENT_KEY;
import static main.java.controller.ControllerConstants.MESSAGE_FROM_KEY;
import static main.java.controller.ControllerConstants.MESSAGE_TO_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ValidationService validationService;

    @RequestMapping(method = POST, value = "/send-message")
    public @ResponseBody boolean sendMessage(@RequestParam Map<String,String> allParams) throws InvalidParameterException {
        String to = allParams.get(MESSAGE_TO_KEY);
        String from = allParams.get(MESSAGE_FROM_KEY);
        String content = allParams.get(MESSAGE_CONTENT_KEY);

        validationService.validateUUID(to, MESSAGE_TO_KEY);
        validationService.validateUUID(from, MESSAGE_FROM_KEY);
        validationService.isNotNullOrEmpty(content, MESSAGE_CONTENT_KEY);

        Message message = new Message();
        message.setDate(new Date());
        message.setTo(UUID.fromString(to));
        message.setFrom(UUID.fromString(from));
        message.setContent(content);

        return messageService.saveMessage(message);
    }

    @RequestMapping(method = POST, value = "/get-messages-to")
    public @ResponseBody List<Message> getMessagesToUser(@RequestParam Map<String,String> allParams) throws InvalidParameterException {
        String accountNumber = allParams.get(ACCOUNT_NUMBER_KEY);
        validationService.validateUUID(accountNumber, ACCOUNT_NUMBER_KEY);
        return messageService.getMessagesToUser(UUID.fromString(accountNumber));
    }

    @RequestMapping(method = POST, value = "/get-messages-from")
    public @ResponseBody List<Message> getMessagesFromUser(@RequestParam Map<String,String> allParams) throws InvalidParameterException {
        String accountNumber = allParams.get(ACCOUNT_NUMBER_KEY);
        validationService.validateUUID(accountNumber, ACCOUNT_NUMBER_KEY);
        return messageService.getMessagesFromUser(UUID.fromString(accountNumber));
    }

    @RequestMapping(method = POST, value = "/get-messages-between")
    public @ResponseBody List<Message> getMessagesBetweenUsers(@RequestParam Map<String,String> allParams) throws InvalidParameterException {
        String accountNumber1 = allParams.get(MESSAGE_TO_KEY);
        String accountNumber2 = allParams.get(MESSAGE_FROM_KEY);

        validationService.validateUUID(accountNumber1, MESSAGE_TO_KEY);
        validationService.validateUUID(accountNumber2, MESSAGE_FROM_KEY);

        return messageService.getMessagesBetweenUsers(UUID.fromString(accountNumber1), UUID.fromString(accountNumber2));
    }
}
