package postchecker;

public class MathMajorStrategy extends MathGenericStrategy implements PostRequirementStrategy {
    @Override
    public void doSpecificChecks() {
        if (requiredCourses.average() < 2) {
            addFailure("You need an average of 2.0+ in " + requiredCourses.toString());
        }

        CourseList twoBsNeeded = new CourseList();
        twoBsNeeded.add(requiredCourses.getCourseByCode("MATA67"));
        twoBsNeeded.add(requiredCourses.getCourseByCode("MATA22"));
        twoBsNeeded.add(requiredCourses.getCourseByCode("MATA37"));
        if (twoBsNeeded.numCoursesSatisfyingGrade(3.0) < 1) {
            addFailure("You need at least a B in 1+ of " + twoBsNeeded);
        }
    }
}
