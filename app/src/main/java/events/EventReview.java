package events;

public class EventReview {
    public String author;
    public String comment;
    public int rating;
    public String eventID;
    public EventReview(String author, String comment, int rating, String eventID) {
        this.author=author;
        this.comment=comment;
        this.rating=rating;
        this.eventID=eventID;
    }
    public EventReview() {}
    public String getAuthor() {return author;};
    public String getComment() {return comment;};
    public int getRating() {return rating;};
    public String getEventID() {return eventID;}
}
