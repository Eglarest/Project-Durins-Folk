package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Contact {

    public enum ContactStatus {ACTIVE, PENDING_LOWER, PENDING_HIGHER, BLOCKED_LOWER, BLOCKED_HIGHER, BLOCKED_BOTH}

    private UUID user1;
    private UUID user2;
    private Date firstContact;
    private Date lastContact;
    private List<UUID> sharedEventIds;
    private ContactStatus contactStatus;

    public void flipStatus() {
        if(contactStatus == ContactStatus.BLOCKED_LOWER) {
            contactStatus = ContactStatus.BLOCKED_HIGHER;
        } else if(contactStatus == ContactStatus.BLOCKED_HIGHER) {
            contactStatus = ContactStatus.BLOCKED_LOWER;
        } else if(contactStatus == ContactStatus.PENDING_LOWER) {
            contactStatus = ContactStatus.PENDING_HIGHER;
        } else if(contactStatus == ContactStatus.PENDING_HIGHER) {
            contactStatus = ContactStatus.PENDING_LOWER;
        }
    }

}
