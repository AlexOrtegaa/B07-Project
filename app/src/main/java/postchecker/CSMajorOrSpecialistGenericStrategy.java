package postchecker;

public abstract class CSMajorOrSpecialistGenericStrategy extends GenericProgramStrategy
        implements PostRequirementStrategy {
    @Override
    public void setRequiredCourses() {
        CourseRequirement discreteMaths = new CourseRequirement("CSCA67", post_repeat_warning);

        discreteMaths.addCourseCode("MATA67");

        requiredCourses = new CourseList();
        requiredCourses.add(discreteMaths);
        requiredCourses.add(new CourseRequirement("CSCA48", post_repeat_warning));
        requiredCourses.add(new CourseRequirement("MATA22", post_repeat_warning));
        requiredCourses.add(new CourseRequirement("MATA31", post_once_warning));
        requiredCourses.add(new CourseRequirement("MATA37", post_repeat_warning));
    }

    @Override
    public void doSpecificChecks() {
        if (requiredCourses.average() < 2.5) {
            addFailure("You need an average of 2.5+ in " + requiredCourses.toString());
        }

        if (requiredCourses.getCourseByCode("CSCA48").grade < 3.0) {
            addFailure("You need at least a B in CSCA48");
        }

        CourseList twoCminusNeeded = new CourseList();
        twoCminusNeeded.add(requiredCourses.getCourseByCode("MATA67"));
        twoCminusNeeded.add(requiredCourses.getCourseByCode("MATA22"));
        twoCminusNeeded.add(requiredCourses.getCourseByCode("MATA37"));
        if (twoCminusNeeded.numCoursesSatisfyingGrade(1.7) < 2) {
            addFailure("You need at least a C- in 2+ of " + twoCminusNeeded);
        }
    }
}
