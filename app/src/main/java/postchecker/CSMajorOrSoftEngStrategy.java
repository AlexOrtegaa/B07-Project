package postchecker;

public class CSMajorOrSoftEngStrategy extends CSMajorOrSpecialistGenericStrategy
        implements PostRequirementStrategy {

    @Override
    public void finalizeMessage() {
        if (qualifies) {
            message = post_yes_msg;
            addNote(post_admission_guarantee);
        }
    }
}
