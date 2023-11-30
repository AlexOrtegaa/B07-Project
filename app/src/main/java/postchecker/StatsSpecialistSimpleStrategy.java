package postchecker;

public class StatsSpecialistSimpleStrategy extends GenericProgramStrategy
        implements PostRequirementStrategy {
    @Override
    void setRequiredCourses() {
        String once_warning = post_once_warning;
        String retake_warning = post_repeat_warning;
        CourseRequirement discreteMaths = new CourseRequirement("CSCA67", retake_warning);

        discreteMaths.addCourseCode("MATA67");

        requiredCourses = new CourseList();
        requiredCourses.add(discreteMaths);
        requiredCourses.add(new CourseRequirement("CSCA08", once_warning));
        requiredCourses.add(new CourseRequirement("MATA22", retake_warning));
        requiredCourses.add(new CourseRequirement("MATA31", once_warning));
        requiredCourses.add(new CourseRequirement("MATA37", retake_warning));
    }

    @Override
    public void finalizeMessage() {
        if (qualifies) {
            message = post_yes_msg;
            addNote(post_admission_warning);
            addNote(post_cs_A_level_msg);
        }
    }

    @Override
    public void doSpecificChecks() {
        if (requiredCourses.average() < 2.5) {
            addFailure("You need an average of 2.5+ in " + requiredCourses.toString());
        }
    }
}
