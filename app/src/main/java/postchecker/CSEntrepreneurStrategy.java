package postchecker;

public class CSEntrepreneurStrategy extends CSMajorOrSpecialistGenericStrategy
        implements PostRequirementStrategy {

    @Override
    public void finalizeMessage() {
        if (qualifies) {
            message = post_yes_msg;
            addNote(post_admission_warning);
            addNote(post_cs_A_level_msg);
            addNote("Competitive supplemental application materials required.");
        }
    }
}
