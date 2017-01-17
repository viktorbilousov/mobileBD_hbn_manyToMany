package DAO;

import model.Service;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl extends BaseDAO<Service> implements ServiseDAO {

    public List<User> getUserByServiceId(int idService) {
        return new ArrayList<User>(getById(idService).getUsersSet());
    }

    public void remove(int id) {
        remove("delete from Service where idService=" + id);
    }

    public Service getById(int id) {
        return get("from Service where idService=" + id);
    }

    public ArrayList<Service> getList(String query) {
        return getList(query, Service.class);
    }

    public ArrayList<Service> getAll() {
        return getList("from Service");
    }

    public Service getLast() {
        int id = getLastId(Service.class, "idService");
        return getById(id);
    }

}
