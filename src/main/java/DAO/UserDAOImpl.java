package DAO;

import model.Service;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

    public ArrayList<User> getAllByStatus(boolean status) {
        return getList("from User u where u.status=" + status);
    }

    public ArrayList<User> getAll() {
        return getList("from User");
    }

    public void addToBalanceInt(int money, int id) {
        User user = getById(id);
        user.setBalance(user.getBalance() + money);
        update(user);
    }

    public List<Service> getServicesByUser(int idUser) {
        return new ArrayList<Service>(getById(idUser).getServicesSet());
    }

    public User getById(int id) {
        return get("from User where idUser=" + id);
    }

    public ArrayList<User> getList(String query) {
        return getList(query, User.class);
    }

    public void remove(int id) {
        remove("delete from User where idUser=" +id);
    }

    public User getLast() {
        int id = getLastId(User.class, "idUser");
        return getById(id);
    }

}


