package DAO;

import model.Service;
import model.User;
import java.util.List;

public interface UserDAO extends BaseInteface<User> {
    List<User> getAllByStatus(boolean status);
    List<User> getAll();
    void addToBalanceInt(int money, int id); // нужно ли?
    List<Service> getServicesByUser(int idUser);
}
