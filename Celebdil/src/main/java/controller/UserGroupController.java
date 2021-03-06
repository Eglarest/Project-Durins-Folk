package main.java.controller;

import com.google.common.base.Strings;
import main.java.data.Address;
import main.java.data.UserGroup;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidParameterException;
import main.java.service.AddressService;
import main.java.service.UserGroupService;
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
import static main.java.controller.ControllerConstants.GROUP_ID_KEY;
import static main.java.controller.ControllerConstants.GROUP_NAME_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AddressService addressService;

    /**
     * This API will take a user (probably by UUID) and return information about the user
     * @return
     */
    @RequestMapping(method = POST, value = "/get-user-group")
    public @ResponseBody UserGroup getUserGroupData(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String groupId = allParams.get(GROUP_ID_KEY);
        validationService.validateUUID(groupId, GROUP_ID_KEY);
        return userGroupService.getUserGroupByGroupId(UUID.fromString(groupId));
    }

    /**
     * This API will take a String and return user's with the string in parts of their profile.
     */
    @RequestMapping(method = GET, value = "/find-user-groups")
    public @ResponseBody List<UserGroup> findUserGroupsContaining(@RequestParam(value="search", defaultValue="") String string) throws InternalFailureException {
        if (Strings.isNullOrEmpty(string)) {
            return new ArrayList<>();
        }
        return userGroupService.getUserGroupsByString(string);
    }

    /**
     * This API will take a user (probably by UUID) and add that user to a group (probably by GroupId)
     * @return
     */
    @RequestMapping(method = POST, value = "/add-user-to-group")
    public @ResponseBody int getUserData(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String accountNumber = allParams.get(ACCOUNT_NUMBER_KEY);
        String groupId = allParams.get(GROUP_ID_KEY);

        validationService.validateUUID(accountNumber, ACCOUNT_NUMBER_KEY);
        validationService.validateUUID(groupId, GROUP_ID_KEY);

        return userGroupService.addUserToUserGroup(UUID.fromString(groupId), UUID.fromString(accountNumber));
    }

    @RequestMapping(method = POST, value = "/create-user-group")
    public @ResponseBody UserGroup createNewUserGroup(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String groupName = allParams.get(GROUP_NAME_KEY);
        Address address = addressService.extractAddress(allParams);

        validationService.isNotNullOrEmpty(groupName, GROUP_NAME_KEY);

        UserGroup usergroup = new UserGroup();
        usergroup.setAddress(address);
        usergroup.setCreationDate(new Date());
        usergroup.setName(groupName);
        return userGroupService.createNewUserGroup(usergroup);
    }
}
