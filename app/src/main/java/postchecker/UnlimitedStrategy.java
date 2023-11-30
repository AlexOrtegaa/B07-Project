package postchecker;

public class UnlimitedStrategy extends GenericProgramStrategy implements PostRequirementStrategy {
    @Override
    void setRequiredCourses() {
        requiredCourses.courses = null;
    }

    @Override
    public void finalizeMessage() {
        message = post_yes_msg;
        addNote(post_admission_guarantee);
    }
}
