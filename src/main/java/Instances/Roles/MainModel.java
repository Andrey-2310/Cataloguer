package Instances.Roles;

/**
 * Created by Андрей on 27.02.2017.
 */
public class MainModel {
    private static int id;
    private static int role;
    private static StringBuffer name;

    public MainModel(int id, int role, String name) {
        MainModel.name =new StringBuffer(name);
        MainModel.id = id;
        MainModel.role =role;
    }

    public MainModel() {
    }

    public static String getName() {
        return String.valueOf(name);
    }

    public static void setName(String name) {
        MainModel.name =new StringBuffer(name);
    }

    public static int getId() {return id;}

    public static void setId(int id) {MainModel.id = id;}

    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        MainModel.role = role;
    }

}
