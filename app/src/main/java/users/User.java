package users;

public class User {
    public boolean admin;
    public String uid;
    public User(boolean admin, String uid){
        this.admin=admin;
        this.uid = uid;
    }
    public User(){

    }
    Boolean getAdmin() {
        return admin;
    }
    String getUid() {
        return uid;
    }
}
