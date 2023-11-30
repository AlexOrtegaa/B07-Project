package postchecker;

public class PostCheckResult {
    private boolean qualifies;
    private String message;

    public PostCheckResult(boolean qualifies, String message) {
        this.qualifies = qualifies;
        this.message = message;
    }

    public boolean getQualifies() {
        return qualifies;
    }

    public String getMessage() {
        return message;
    }
}
