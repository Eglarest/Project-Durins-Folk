package main.java.service;

import com.google.common.base.Strings;
import main.java.exception.DateTimeFormatException;
import main.java.exception.InvalidParameterException;
import main.java.exception.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class DateTimeService {

    @Autowired
    ValidationService validationService;

    public enum DateTimeFormat {ISO8601}

    public Date parse(String string, DateTimeFormat dateTimeFormat) throws NotImplementedException, DateTimeFormatException {
        if(dateTimeFormat == DateTimeFormat.ISO8601) {
            System.out.println("ISO: " + parseISO8601(string));
            System.out.println("Generic: " + parseGeneric(string, "YYYY-MM-DDThh:mm:ss+Oh:Om"));
            return parseISO8601(string);
        }
        throw new NotImplementedException("DateTimeFormat.parse not yet implemented for: " + dateTimeFormat);
    }

    private Timestamp parseISO8601(String string) throws DateTimeFormatException {
        String dateTimeFormatMessage = "String \"" + string + "\" is not in ISO8601 format";
        // Make sure string is usable
        if(Strings.isNullOrEmpty(string) || Strings.isNullOrEmpty(string.trim())) {
            throw new DateTimeFormatException(dateTimeFormatMessage);
        }
        string = string.trim();

        try {
            validationService.validateISODateTime(string, "Passed into parseISO8601");
        } catch (InvalidParameterException e) {
            throw new DateTimeFormatException(dateTimeFormatMessage + " | " + e.getMessage(), e);
        }

        // Separate into date, time, and offset components
        String date = string.substring(0, string.indexOf('T'));
        String timeAndOffset = string.substring(string.indexOf('T')+1);
        String time;
        String offset = null;
        Boolean offsetPlus = null;
        if(timeAndOffset.contains("+")) {
            time = timeAndOffset.substring(0, string.indexOf('+')-(date.length()+1));
            offset = timeAndOffset.substring(string.indexOf('+')-date.length());
            offsetPlus = true;
        } else if(timeAndOffset.contains("-")) {
            time = timeAndOffset.substring(0, string.indexOf('-')-(date.length()+1));
            offset = timeAndOffset.substring(string.indexOf('-')-date.length());
            offsetPlus = false;
        } else {
            time = timeAndOffset;
        }

        // Separate into year, month, day
        String year = date.substring(0, date.indexOf('-'));
        date = date.substring(date.indexOf('-')+1);
        String month = date.substring(0, date.indexOf('-'));
        String day = date.substring(date.indexOf('-')+1);

        // Separate into hours, minutes, seconds
        String hours = time.substring(0, time.indexOf(':'));
        time = time.substring(time.indexOf(':')+1);
        String minutes = time.substring(0, time.indexOf(':'));
        String seconds = time.substring(time.indexOf(':')+1);

        int hourTime = Integer.parseInt(hours);
        int minuteTime = Integer.parseInt(minutes);
        int secondTime = Integer.parseInt(seconds);

        if(offsetPlus != null) {
            String offsetHours = offset.substring(0,offset.indexOf(':'));
            String offsetMinutes = offset.substring(offset.indexOf(':')+1);
            if(offsetPlus) {
                hourTime -= Integer.parseInt(offsetHours);
                minuteTime -= Integer.parseInt(offsetMinutes);
            } else {
                hourTime += Integer.parseInt(offsetHours);
                minuteTime += Integer.parseInt(offsetMinutes);
            }
        }

        return Timestamp.valueOf(String.format("%s-%s-%s %s:%s:%s",year, month, day, hourTime, minuteTime, secondTime));
    }

    // Acceptable formats must contain: YYYY MM DD HH MM SS (optional hh mm ss for offsets)
    // Note: Formats will remove whitespace at the start/end
    private Timestamp parseGeneric(String string, String format) throws DateTimeFormatException {
        // make sure the format is acceptable
        if(Strings.isNullOrEmpty(format)) {
            throw new DateTimeFormatException("format cannot be null or empty:" +
                    " Acceptable formats must contain: YYYY MM DD hh mm ss (optionally: Oh Om Os for offsets)");
        }
        format = format.trim();

        final List<String> requiredElements = new ArrayList<>();
        requiredElements.add("YYYY");
        requiredElements.add("MM");
        requiredElements.add("DD");
        requiredElements.add("hh");
        requiredElements.add("mm");
        requiredElements.add("ss");

        final List<String> optionalElements = new ArrayList<>();
        optionalElements.add("+");
        optionalElements.add("Oh");
        optionalElements.add("Om");
        optionalElements.add("Os");

        // get the format ordering:
        List<String> formattableElements = new ArrayList<>();
        formattableElements.addAll(requiredElements);
        formattableElements.addAll(optionalElements);

        List<String> order = new ArrayList<>();
        String firstElement = getFirst(format, formattableElements);
        while(firstElement != null) {
            order.add(firstElement);
            formattableElements.remove(firstElement);
            firstElement = getFirst(format, formattableElements);
        }

        formattableElements.clear();
        formattableElements.addAll(requiredElements);
        formattableElements.addAll(optionalElements);

        List<String> missingElements = new ArrayList<>();
        requiredElements.forEach(element -> {if(!order.contains(element)) {
            missingElements.add(element);
        }});

        if(!missingElements.isEmpty()) {
            throw new DateTimeFormatException(String.format("DateTime format does not contain required strings. Missing Strings: %s",
                    missingElements));
        }

        int stringTraversed = 0;
        while(!order.isEmpty()) {
            String element = order.get(0);
            int elementIndex = format.indexOf(element);
            System.out.println("Format: " + format + ". Element: " + element + ". Index: " + elementIndex);
            String formatting = format.substring(stringTraversed, elementIndex);
            String stringFormatting = string.substring(stringTraversed, elementIndex);
            if(!formatting.equals(stringFormatting)) {
                throw new DateTimeFormatException(String.format("DateTime string does not match format at chars: %s-%s."+
                                " String: \"%s\". Format: \"%s\"", stringTraversed, elementIndex, string, format));
            }
            stringTraversed = elementIndex + element.length();
            order.remove(element);
        }

        HashMap<String, String> values = new HashMap<>();

        for(String element : formattableElements) {
            int index = format.indexOf(element);
            if(index > -1) {
                values.put(element, string.substring(index, index + element.length()));
            }
        }

        if(values.get("+") != null) {
            int offsetOperator;
            if (values.get("+").equals("+")) {
                offsetOperator = 1;
            } else if (values.get("+").equals("-")) {
                offsetOperator = -1;
            } else {
                throw new DateTimeFormatException(String.format("DateTime string does not match format. String: \"%s\". Format: \"%s\"." +
                        " Offset Operator \"+\" must be '+' or '-'.", string, format));
            }

            if (values.get("Oh") != null) {
                int hour = Integer.parseInt(values.get("hh"));
                hour -= offsetOperator * Integer.parseInt(values.get("Oh"));
                values.put("hh", String.valueOf(hour));
            }
            if (values.get("Om") != null) {
                int minute = Integer.parseInt(values.get("mm"));
                minute -= offsetOperator * Integer.parseInt(values.get("Om"));
                values.put("mm", String.valueOf(minute));
            }
            if (values.get("Os") != null) {
                int second = Integer.parseInt(values.get("ss"));
                second -= offsetOperator * Integer.parseInt(values.get("Os"));
                values.put("ss", String.valueOf(second));
            }
        }

        System.out.println(String.format("%s-%s-%s %s:%s:%s",values.get("YYYY"), values.get("MM"), values.get("DD"),
                values.get("hh"), values.get("mm"), values.get("ss")));
        return Timestamp.valueOf(String.format("%s-%s-%s %s:%s:%s",values.get("YYYY"), values.get("MM"), values.get("DD"),
                values.get("hh"), values.get("mm"), values.get("ss")));
    }

    private String getFirst(String string, List<String> elements) {
        String selected = null;
        for(String element : elements) {
            if(string.contains(element) && (selected == null || string.indexOf(element) < string.indexOf(selected)) ) {
                selected = element;
            }
        }
        return selected;
    }
}
