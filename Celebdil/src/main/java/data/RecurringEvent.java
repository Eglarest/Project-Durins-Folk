package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class RecurringEvent {

    private UUID recurringId;
    private List<UUID> activityIds;
    private Date startDate;
    private Date length;
    private Date frequency;
    private Date endDate;
    private List<UUID> children;
    private Address address;

}
