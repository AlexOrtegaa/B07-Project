package postchecker;

public class StatsMajorStrategy extends GenericProgramStrategy
        implements PostRequirementStrategy {
    @Override
    public void setRequiredCourses() {

        CourseRequirement programming = new CourseRequirement("CSCA08", post_once_warning);
        CourseRequirement calcI = new CourseRequirement("MATA30", post_once_warning);
        CourseRequirement calcII = new CourseRequirement("MATA36", post_repeat_warning);

        programming.addCourseCode("CSCA20");
        calcI.addCourseCode("MATA31");
        calcII.addCourseCode("MATA37");

        requiredCourses = new CourseList();
        requiredCourses.add(programming);
        requiredCourses.add(new CourseRequirement("MATA22", post_repeat_warning));
        requiredCourses.add(calcI);
        requiredCourses.add(calcII);
    }

    @Override
    public void finalizeMessage() {
        if (qualifies) {
            message = post_yes_msg;
            addNote(post_admission_guarantee);
        }
    }

    @Override
    public void doSpecificChecks() {
        if (requiredCourses.average() < 2.3) {
            addFailure("You need an average of 2.3+ in " + requiredCourses.toString());
        }
    }
}
