package Instances.Roles;

/**
 * Created by Андрей on 27.02.2017.
 */
public class User extends MainModel{

    private String email;
    private String password;

    public User(int id, int role, String email, String name, String password){
        super(id, role, name);
        this.email=email;
        this.password=password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
