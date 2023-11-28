package postchecker;

import java.util.ArrayList;
import java.util.List;

public class CourseRequirement {
    List<String> courseCodes;
    double grade;
    String warning;

    public String getWarning() {
        if (warning != null) {
            return warning;
        }
        return "";
    }

    public void addCourseCode(String courseCode) {
        courseCodes.add(courseCode);
    }

    public CourseRequirement(String courseCode) {
        courseCodes = new ArrayList<>();
        addCourseCode(courseCode);
    }

    public CourseRequirement(String courseCode, String warning) {
        courseCodes = new ArrayList<>();
        addCourseCode(courseCode);
        this.warning = warning;
    }

    public boolean hasCourseCode(String courseCode) {
        return courseCodes.contains(courseCode);
    }

    @Override
    public String toString() {
        String result = "";
        for (String courseCode : courseCodes) {
            if (result.length() > 0) {
                result += "/";
            }
            result += courseCode;
        }
        return result;
    }

    public boolean creditEarned() {
        return (grade > 0);
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }
}
