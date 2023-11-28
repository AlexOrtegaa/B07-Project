package postchecker;

public class CSMinorStrategy extends GenericProgramStrategy implements PostRequirementStrategy {
    @Override
    public void setRequiredCourses() {
        requiredCourses = new CourseList();

        CourseRequirement discreteMaths = new CourseRequirement("CSCA67");
        CourseRequirement linAlgebra = new CourseRequirement("MATA22");
        CourseRequirement calcI = new CourseRequirement("MATA30");

        discreteMaths.addCourseCode("MATA67");
        linAlgebra.addCourseCode("MATA23");
        calcI.addCourseCode("MATA31");
        calcI.addCourseCode("MATA32");

        requiredCourses.add(new CourseRequirement("CSCA08"));
        requiredCourses.add(new CourseRequirement("CSCA48"));
        requiredCourses.add(discreteMaths);
        requiredCourses.add(linAlgebra);
        requiredCourses.add(calcI);
    }

    @Override
    public void finalizeMessage() {
        if (qualifies) {
            message = post_yes_msg;
            addNote(post_admission_warning);
            addNote("Competitive CSCA48, CSC/MATA67, MATA22/23 required.");
        }
    }

    @Override
    public void doSpecificChecks() {

    }
}
