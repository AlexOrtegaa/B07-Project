package events;

//Please use the following method for creating new Events in the database:
//
//Skip the next 2 lines if you have a different way of getting the DatabaseReference
//FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
//DatabaseReference ref = db.getReference("");
//
//DatabaseReference eventsRef = ref.child("events");
//
//DatabaseReference newEventRefRef = eventsRef.push();
//newEventRefRef.setValueAsync(new Event("00/00/0000", 50, "description", "email"));
//or simplified:
//eventsRef.push().setValueAsync(new Event("00/00/0000", 50, "description", "email"));
public class Event {
    public String date;
    public String title;
    public int maxParticipants;
    public String description;
    public String author;
    public double avgRating;
    public int numParticipants;
    public int numReviews;
    public Event(String date, String title, int maxParticipants, String description, String author){
        this.date = date;
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.author = author;
        this.numParticipants = 0;
        this.avgRating = 0;
    }
    public Event(){

    }
    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getAuthor() {
        return author;
    }
    public int getMaxParticipants() {
        return maxParticipants;
    }

}
