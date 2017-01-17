package model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "service")
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idService")
    private int idService;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "price")
    private int price;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_has_service",
            joinColumns = @JoinColumn(name = "idService"),
            inverseJoinColumns = @JoinColumn(name = "idUser")
    )
    private Set<User> usersSet = new HashSet<User>();

    public Service(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Service(){};

    //region set\get
    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<User> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<User> usersSet) {
        this.usersSet = usersSet;
    }
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (getIdService() != service.getIdService()) return false;
        if (getPrice() != service.getPrice()) return false;
        if (!getName().equals(service.getName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getIdService();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getPrice();
        return result;
    }

    @Override
    public String toString() {
        String line = "Service{" +
                "idService=" + idService +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", usersSet=( ";
                for(User user : usersSet){
                    line+= user.getName();
                    line+= ", ";
                }
                line += ") }";
        return line;
    }

}
