package model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Proxy(lazy = false)
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "balance")
    private int balance;

    @Column(name = "status")
    private boolean status;


    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "user_has_service",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idService")
    )
    private Set<Service> servicesSet = new HashSet<Service>();

    public User(){};

    public User(String name, int balance, boolean status) {
        this.name = name;
        this.balance = balance;
        this.status = status;
    }

    //region set\get

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlocked() {
        return !status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Service> getServicesSet() {
        return servicesSet;
    }

    public void setServicesSet(Set<Service> servicesSet) {
        this.servicesSet = servicesSet;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getIdUser() != user.getIdUser()) return false;
        if (isBlocked() != user.isBlocked()) return false;
        if (!getName().equals(user.getName())) return false;
        return true;
    }

    @Override
    public String toString() {
        String line =  "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                ", servicesSet=( ";
        for(Service service :servicesSet) {
            line+= service.getName() + ", ";
        }
                line +=" ) }";
        return line;
    }

    @Override
    public int hashCode() {
        int result = getIdUser();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getBalance();
        result = 31 * result + (status ? 1 : 0);
        return result;
    }
}
