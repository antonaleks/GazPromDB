package entity;

import enums.Access;

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

    public static User getCurrentUser() {
        if(user==null)user=new User();
        return user;
    }

    public String getUsersRights(){
        String rights = "";
        if(Access.checkAccess(this.access, Access.Right.WRITE))rights+="-Запись\n";
        if(Access.checkAccess(this.access, Access.Right.ADDUSER))rights+="-Добавление пользователей\n";
        if(Access.checkAccess(this.access, Access.Right.READ))rights+="-Чтение\n";
        if(Access.checkAccess(this.access, Access.Right.REMOVE))rights+="-Удаление данных\n";
        if(Access.checkAccess(this.access, Access.Right.UPDATE))rights+="-Обновление данных\n";
        return rights;
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
