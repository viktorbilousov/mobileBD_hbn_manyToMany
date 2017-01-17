package DAO;

import model.UserHasService;
import java.util.ArrayList;


public interface UserHasServiceDAO {
    ArrayList<UserHasService> getByIdUser(int idUser);
    ArrayList<UserHasService>getByIdService(int idService);
    void removePaare(int idUser, int idService);
    void removeAllUserById(int idUser);
    void removeAllServiceById(int idService);
    ArrayList<UserHasService> getAll();
}
