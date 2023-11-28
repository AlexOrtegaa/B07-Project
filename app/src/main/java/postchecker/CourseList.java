package postchecker;

import java.util.ArrayList;
import java.util.List;

public class CourseList {
    List<CourseRequirement> courses;

    public CourseRequirement get(int i) {
        if (courses == null || i >= courses.size()) {
            return null;
        }
        return courses.get(i);
    }

    public CourseList() {
        courses = new ArrayList<>();
    }

    public CourseRequirement getCourseByCode(String courseCode) {
        for (CourseRequirement c : courses) {
            if (c.hasCourseCode(courseCode)) {
                return c;
            }
        }
        return null;
    }

    public void add(CourseRequirement course) {
        courses.add(course);
    }

    public int numCoursesSatisfyingGrade(double grade) {
        int num = 0;

        for (CourseRequirement c : courses) {
            if (c.grade >= grade) {
                num++;
            }
        }
        return num;
    }

    public double average() {
        double sum = 0;
        double num = 0;

        for (CourseRequirement c : courses) {
            sum += c.grade;
            num += 1;
        }
        if (num == 0) {
            return 0;
        }
        return sum / num;
    }

    @Override
    public String toString() {
        String result = "";
        for (CourseRequirement course : courses) {
            if (result.length() > 0) {
                result += ", ";
            }
            result += course.toString();
        }
        return result;
    }

    public int size() {
        if (courses != null) {
            return courses.size();
        }
        return 0;
    }
}
