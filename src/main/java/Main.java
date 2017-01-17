import DAO.ServiceDAOImpl;
import DAO.UserDAOImpl;
import DAO.UserHasServiceDAOImpl;
import model.Service;
import model.User;
import org.hibernate.Session;
import role.Admin;
import role.Subscriber;
import runner.Command;
import runner.Runner;
import runner.RunnerFunctionBD;
import util.HibernateUtil;

import java.awt.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Runner runner = new Runner(new RunnerFunctionBD());
        runner.start();
    }

    public static final void outList(ArrayList list)
    {
        for(Object o : list){
            System.out.println(o.toString());
        }
    }

}
