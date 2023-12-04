package events;

public class EventRSVP {
    public String uid;
    public String event;
    public EventRSVP(String uid, String event){
        this.uid = uid;
        this.event = event;
    }
    public EventRSVP(){
    }


    public String getUid() {
        return uid;
    }
    public String getEvent() { return event; }
}
