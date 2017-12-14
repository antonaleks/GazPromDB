package entity;

public class User {
    private String login;
    private String password;
    private int access;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAccess(String access) {
        this.access = Integer.parseInt(access,2);
    }

    private static User user;

    public static User getInstance() {
        if(user==null)user=new User();
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public int getAccess() {
        return access;
    }
}
