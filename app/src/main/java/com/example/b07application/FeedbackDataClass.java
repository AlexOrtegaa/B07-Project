package com.example.b07application;

import org.w3c.dom.Text;

public class FeedbackDataClass {
    public FeedbackDataClass(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    String comment;

    public FeedbackDataClass() {
    }

    int rating;

}
