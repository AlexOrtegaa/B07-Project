package postchecker;

public abstract class GenericProgramStrategy implements PostRequirementStrategy {
    final String post_yes_msg = "You qualify to apply to this program.";
    final String post_no_msg_header = "You have not met the following requirements:";
    final String post_admission_warning = "Qualification does not imply admission.";
    final String post_admission_guarantee = "Qualification implies admission.";
    final String post_cs_A_level_msg = "Competitive A-level grades required.";
    final String post_repeat_warning = "If you completed this course more than once, use the highest grade from your 1st or 2nd attempt.";
    final String post_once_warning = "If you completed this course more than once, use the grade from your first passing attempt.";

    boolean qualifies;
    String message;
    public CourseList requiredCourses;

    abstract void setRequiredCourses();

    public GenericProgramStrategy() {
        qualifies = true;
        requiredCourses = new CourseList();
        setRequiredCourses();
    }

    void addNote(String note) {
        message += "\n" + note;
    }

    void addFailure(String detail) {
        if (qualifies) {
            message = post_no_msg_header;
            qualifies = false;
        }
        addNote(detail);
    }

    public PostCheckResult makeResult() {
        return new PostCheckResult(qualifies, message);
    }

    public void checkCredit(CourseRequirement course) {
        if (!course.creditEarned()) {
            addFailure("You need credit for " + course);
        }
    }

    public abstract void finalizeMessage();

    public void doSpecificChecks() {
    }

    @Override
    public PostCheckResult checkRequirements() {
        if (requiredCourses.courses != null) {
            for (CourseRequirement c : requiredCourses.courses) {
                checkCredit(c);
            }
        }
        doSpecificChecks();
        finalizeMessage();
        return makeResult();
    }
}
