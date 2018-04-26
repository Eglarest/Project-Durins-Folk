package data;

public class Message {

    private final long id;
    private final String content;
    private final String from;
    private final String to;

    public Message(long id, String content, String from, String to) {
        this.id = id;
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String toString() {
        return ("Message " + id + ": " + from + " says, \"" + content + "\" to " + to +"!");
    }
}