package InterfacesDAO.RolesDAO;
import Instances.Roles.MainModel;

/**
 * Created by Андрей on 28.02.2017.
 */
public interface RoleDAO<T extends MainModel> {
    public void update(T model);

}
