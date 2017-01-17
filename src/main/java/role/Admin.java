package role;

import DAO.ServiceDAOImpl;
import DAO.UserDAOImpl;
import DAO.UserHasServiceDAO;
import DAO.UserHasServiceDAOImpl;
import model.Service;
import model.User;
import model.UserHasService;
import org.hibernate.Session;

import java.util.ArrayList;
public class Admin {
    private User user = null;
    private ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private UserHasServiceDAOImpl userHasServiceDAO = new UserHasServiceDAOImpl();

    public int addNewUser(User user){
        userDAO.save(user);
        return userDAO.getLast().getIdUser();
    }
    public void updateStatusUserById(int idUser, boolean status){
        user = userDAO.getById(idUser);
        user.setStatus(status);
        userDAO.update(user);
        user = null;
    }

    public ArrayList<User> getListAllUser(){
        return userDAO.getAll();
    }
    public ArrayList<User> getListUserByStatus(boolean status){
        return userDAO.getAllByStatus(status);
    }
    public void removeUserById(int idUser){

        for(UserHasService userHasService : userHasServiceDAO.getByIdUser(idUser)){
            userHasServiceDAO.removePaare(userHasService.getIdUSer(), userHasService.getIdService());
        }
        userDAO.remove(idUser);
    }

    public int addService(Service service){
        serviceDAO.save(service);
        return serviceDAO.getLast().getIdService();
    }

    public void removeService(int idService){
        for(UserHasService userHasService : userHasServiceDAO.getByIdService(idService)){
            userHasServiceDAO.removePaare(userHasService.getIdUSer(), userHasService.getIdService());
        }
        serviceDAO.remove(idService);
    }

    public ArrayList<Service> getListAllServices(){
        return serviceDAO.getAll();
    }
}
