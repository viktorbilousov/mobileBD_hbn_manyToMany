package role;

import DAO.ServiceDAOImpl;
import DAO.UserDAOImpl;
import model.Service;
import model.User;
import model.UserHasService;

import java.util.ArrayList;

public class Subscriber {
    private UserDAOImpl userDAO = new UserDAOImpl();
    private ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
    private UserHasService userHasService = new UserHasService();
    private User thisUser = null;
    private Service service = null;

    public Subscriber(int idUser){
        thisUser = userDAO.getById(idUser);
    }

    public User getUser() {
        return thisUser;
    }

    public void connectService(int idService){
        service = serviceDAO.getById(idService);

        if(thisUser.getServicesSet().contains(service))
            return;

        thisUser.getServicesSet().add(service);
        service.getUsersSet().add(thisUser);
        serviceDAO.update(service);
        userDAO.update(thisUser);
    }
    public void disconnectService(int idService){
        service = serviceDAO.getById(idService);
        thisUser.getServicesSet().remove(service);
        userDAO.update(thisUser);
    }

    public void addMoney(int money){
        if(money <= 0)
            return;
        thisUser.setBalance(thisUser.getBalance() + money);
        userDAO.update(thisUser);
    }
    public void makeCall(){
        int pay  = 0;
        if(thisUser.isBlocked())
            return;

        for(Service s : thisUser.getServicesSet()) {
            pay += s.getPrice();
        }
        thisUser.setBalance(thisUser.getBalance() - pay);
        userDAO.update(thisUser);
    }

    public ArrayList<Service> getAllServicesList(){
        return serviceDAO.getAll();
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                thisUser +
                '}';
    }
}
