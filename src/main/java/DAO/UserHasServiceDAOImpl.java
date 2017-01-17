package DAO;

import model.UserHasService;

import java.util.ArrayList;
import java.util.List;

public class UserHasServiceDAOImpl extends BaseDAO<UserHasService> implements UserHasServiceDAO {
    public ArrayList<UserHasService> getByIdUser(int idUser) {
        return getList("from UserHasService where idUser=" + idUser);
    }

    public ArrayList<UserHasService> getByIdService(int idService) {
        return getList("from UserHasService where idService=" + idService);
    }

    public void removePaare(int idUser, int idService) {
        remove("delete from UserHasService where idUser=" + idUser + " and idService=" +idService);
    }

    public void removeAllUserById(int idUser) {
        for (UserHasService uHs : getByIdUser(idUser)){
            removePaare(uHs.getIdUSer(), uHs.getIdService());
        }
    }

    public void removeAllServiceById(int idService) {
        for (UserHasService uHs : getByIdUser(idService)){
            removePaare(uHs.getIdUSer(), uHs.getIdService());
        }
    }

    public ArrayList<UserHasService> getList(String query) {
        return getList(query, UserHasService.class);
    }

    public ArrayList<UserHasService> getAll() {
        return getList("from UserHasService");
    }
}
