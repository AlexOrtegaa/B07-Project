package com.example.b07application;

public class ComplaintDataClass {

    public ComplaintDataClass(String text, String description, String date, String time) {
        Text = text;
        Desc = description;
        Date = date;
        Time = time;
    }

    private String Text;
    private String Desc;
    private String Date;
    private String Time;

    public String getText() {
        return Text;
    }

    public String getDesc() {
        return Desc;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public ComplaintDataClass(){

    }
}
