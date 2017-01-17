package DAO;

import model.Service;
import model.User;

import java.util.List;

public interface ServiseDAO extends BaseInteface<Service> {
   List<User> getUserByServiceId(int idService); // нужно ли
   List<Service> getAll();
}
