package Instances.Roles;

/**
 * Created by Андрей on 27.02.2017.
 */
public class Admin extends MainModel {
    private String password;

    public Admin(int id, int role, String name, String password) {
        super(id, role, name);
        this.password=password;
    }

    public Admin() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
