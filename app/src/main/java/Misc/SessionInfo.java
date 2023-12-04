package Misc;

public class SessionInfo {
    public boolean isAdmin;
    private SessionInfo() {
        isAdmin=false;
    };
    private static SessionInfo sessionInfo;
    public static SessionInfo getInstance() {
        if(sessionInfo == null)
            sessionInfo = new SessionInfo();
        return sessionInfo;
    }

}
