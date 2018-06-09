package main.java.controller;

import com.google.common.base.Strings;
import main.java.data.Activity;
import main.java.data.Address;
import main.java.data.DatabaseEvent;
import main.java.exception.DateTimeFormatException;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidParameterException;
import main.java.exception.NotImplementedException;
import main.java.service.ActivityService;
import main.java.service.AddressService;
import main.java.service.DateTimeService;
import main.java.service.DateTimeService.DateTimeFormat;
import main.java.service.EventService;
import main.java.service.UserGroupService;
import main.java.service.UserService;
import main.java.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static main.java.controller.ControllerConstants.ACCOUNT_NUMBER_KEY;
import static main.java.controller.ControllerConstants.ACTIVITY_NAME_KEY;
import static main.java.controller.ControllerConstants.EVENT_ID_KEY;
import static main.java.controller.ControllerConstants.EVENT_NAME_KEY;
import static main.java.controller.ControllerConstants.GROUP_ID_KEY;
import static main.java.controller.ControllerConstants.ISO8601_DATE_TIME_TIMEZONE_KEY;
import static main.java.controller.ControllerConstants.ISO8601_LENGTH_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private DateTimeService dateTimeService;

    /**
     * This API will take in the user (probably through their UUID) and a range of Dates (probably must
     * be consecutive for now) and return an array containing all of the events that user currently has
     * on their calendar for those days (past -> attended, future -> planning to attend/undecided)
     * @return
     */
    @RequestMapping(method = POST, value = "/user-events")
    public @ResponseBody List<DatabaseEvent> getUserEvents(@RequestParam Map<String, String> allParams) throws InvalidParameterException, InternalFailureException, DateTimeFormatException, NotImplementedException {
        String accountNumberString = allParams.get(ACCOUNT_NUMBER_KEY);
        String dateTime = allParams.get(ISO8601_DATE_TIME_TIMEZONE_KEY);

        validationService.validateISODateTime(dateTime, ISO8601_DATE_TIME_TIMEZONE_KEY);
        validationService.validateUUID(accountNumberString, ACCOUNT_NUMBER_KEY);

        Date date = new Date(dateTimeService.parse(dateTime,DateTimeFormat.ISO8601).getTime()/1000);

        return eventService.getEventsByUserIdForDate(UUID.fromString(accountNumberString), date);
    }

    /**
     * This API will take an eventId and return the event
     */
    @RequestMapping(method = GET, value = "/get-event")
    public @ResponseBody DatabaseEvent getEvent(@RequestParam(value="event", defaultValue="") String eventId) throws InvalidParameterException, InternalFailureException {
        if(Strings.isNullOrEmpty(eventId)) {
            throw new InvalidParameterException("Must provide event to get (format: /get-events?event=<eventId>");
        }
        validationService.validateUUID(eventId, "event");

        return eventService.getEventByEventId(UUID.fromString(eventId));
    }

    /**
     * This API will take in the group (probably through their UUID) and a range of Dates (probably must
     * be consecutive for now) and return an array containing all of the events that group currently has
     * on their calendar for those days (past -> attended, future -> planning to attend/undecided)
     * @return
     */
    @RequestMapping(method = POST, value = "/group-events")
    public @ResponseBody List<DatabaseEvent> getGroupEvents(@RequestParam Map<String, String> allParams) throws InvalidParameterException, InternalFailureException, DateTimeFormatException, NotImplementedException {
        String groupIdString = allParams.get(GROUP_ID_KEY);
        String dateTime = allParams.get(ISO8601_DATE_TIME_TIMEZONE_KEY);

        validationService.validateUUID(groupIdString, GROUP_ID_KEY);
        validationService.validateISODateTime(dateTime, ISO8601_DATE_TIME_TIMEZONE_KEY);

        Date date = new Date(dateTimeService.parse(dateTime,DateTimeFormat.ISO8601).getTime()/1000);

        return eventService.getEventsByGroupIdForDate(UUID.fromString(groupIdString), date);
    }

    @RequestMapping(method = POST, value = "/create-event") // start/end, start/length, start/(years,months,days,hours,min,sec)
    public @ResponseBody int createEvent(@RequestParam Map<String, String> allParams) throws InvalidParameterException, InternalFailureException, DateTimeFormatException, NotImplementedException {
        String accountNumberString = allParams.get(ACCOUNT_NUMBER_KEY);
        String eventName = allParams.get(EVENT_NAME_KEY);
        String activityName = allParams.get(ACTIVITY_NAME_KEY);
        String dateTime = allParams.get(ISO8601_DATE_TIME_TIMEZONE_KEY);
        String isoLength = allParams.get(ISO8601_LENGTH_KEY);
        Address address = addressService.extractAddress(allParams);

        validationService.validateUUID(accountNumberString, ACCOUNT_NUMBER_KEY);
        validationService.isNotNullOrEmpty(eventName, EVENT_NAME_KEY);
        validationService.isNotNullOrEmpty(activityName, ACTIVITY_NAME_KEY);
        validationService.validateISODateTime(dateTime, ISO8601_DATE_TIME_TIMEZONE_KEY);
        validationService.validateISODateTime(isoLength, ISO8601_LENGTH_KEY);

        Activity activity = activityService.getActivityByName(activityName);
        List<UUID> activityIds = new ArrayList<>();
        if(activity != null) {
            activityIds.add(activity.getActivityId());
        }
        List<UUID> attendingUsers = new ArrayList<>();
        attendingUsers.add(UUID.fromString(accountNumberString));

        Date date = new Date(dateTimeService.parse(dateTime, DateTimeFormat.ISO8601).getTime()/1000);
        LocalDateTime localLength = LocalDateTime.parse(isoLength, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Date length = new Date(localLength.toEpochSecond(ZoneOffset.UTC));

        DatabaseEvent databaseEvent = new DatabaseEvent();
        databaseEvent.setStartDate(date);
        databaseEvent.setLength(length);
        databaseEvent.setAddress(address);
        databaseEvent.setActivities(activityIds);
        databaseEvent.setName(eventName);
        databaseEvent.setAttendingUsers(attendingUsers);
        return eventService.createNewEvent(databaseEvent);
    }

    @RequestMapping(method = POST, value = "/join-event")
    public @ResponseBody boolean joinEvent(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String accountNumberString = allParams.get(ACCOUNT_NUMBER_KEY);
        String eventIdString = allParams.get(EVENT_ID_KEY);

        validationService.validateUUID(accountNumberString, ACCOUNT_NUMBER_KEY);
        validationService.validateUUID(eventIdString, EVENT_ID_KEY);

        return eventService.addUserToEvent(UUID.fromString(eventIdString), UUID.fromString(accountNumberString));
    }

    @RequestMapping(method = POST, value = "/support-event")
    public @ResponseBody boolean supportEvent(@RequestParam Map<String,String> allParams) throws InvalidParameterException, InternalFailureException {
        String groupId = allParams.get(GROUP_ID_KEY);
        String eventIdString = allParams.get(EVENT_ID_KEY);

        validationService.validateUUID(groupId, GROUP_ID_KEY);
        validationService.validateUUID(eventIdString, EVENT_ID_KEY);

        return eventService.addUserGroupToEvent(UUID.fromString(eventIdString), UUID.fromString(groupId));
    }

}
