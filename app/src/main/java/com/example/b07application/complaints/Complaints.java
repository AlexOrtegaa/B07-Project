package com.example.b07application.complaints;

public class Complaints {
    public String Date;

    public String Time;
    public String Desc;
    public String Text;

    public Complaints(String date, String time, String desc, String text){
        this.Date = date;
        this.Time = time;
        this.Desc = desc;
        this.Text = text;
    }
    public Complaints(){
    }
}
