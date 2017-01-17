package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_has_service")
public class UserHasService implements Serializable{
    @Id
    @Column(name = "idUser")
    private int idUSer;

    @Id
    @Column(name = "idService")
    private int idService;

    public int getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(int idUSer) {
        this.idUSer = idUSer;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHasService)) return false;

        UserHasService that = (UserHasService) o;

        if (getIdUSer() != that.getIdUSer()) return false;
        return getIdService() == that.getIdService();
    }

    @Override
    public int hashCode() {
        int result = getIdUSer();
        result = 31 * result + getIdService();
        return result;
    }


}
