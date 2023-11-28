package postchecker;

public abstract class MathGenericStrategy extends GenericProgramStrategy
        implements PostRequirementStrategy {
    @Override
    public void finalizeMessage() {
        if (qualifies) {
            message = post_yes_msg;
            addNote(post_admission_guarantee);
        }
    }
    @Override
    public void setRequiredCourses() {
        CourseRequirement discreteMaths = new CourseRequirement("CSCA67", post_repeat_warning);

        discreteMaths.addCourseCode("MATA67");

        requiredCourses = new CourseList();
        requiredCourses.add(discreteMaths);
        requiredCourses.add(new CourseRequirement("MATA22", post_repeat_warning));
        requiredCourses.add(new CourseRequirement("MATA31", post_once_warning));
        requiredCourses.add(new CourseRequirement("MATA37", post_repeat_warning));
    }
}
