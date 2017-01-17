package runner;

import DAO.ServiceDAOImpl;
import DAO.UserDAOImpl;
import DAO.UserHasServiceDAOImpl;
import model.Service;
import model.User;
import role.Admin;
import role.Subscriber;

import java.util.List;

public class RunnerFunctionBD implements RunnerFunction{

    private ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private UserHasServiceDAOImpl userHasServiceDAO = new UserHasServiceDAOImpl();
    private Subscriber subscriber = null;
    private Admin admin = null;

    public void singIn(Command command) {
        //log admin
        //log sub [id]
        if(command.getCommands().size() <= 1) {
            System.out.println("error command input");
            return;
        }
        String secondWord = command.getCommands().get(1);
        if(secondWord.equals(Commands.ADMIN)){
            if(subscriber != null){
                System.out.println("log out subscriber!");
                subscriber = null;
            }
            admin = new Admin();
            System.out.println("Admin log in!");
            return;
        }
        if(secondWord.equals(Commands.USER)){
            if(command.getParams().size() == 0){
                System.out.println("error log in sub! error param");
                return;
            }
            if(admin != null){
                System.out.println("log out admin!");
                admin = null;
            }
            int idSub = Integer.parseInt(command.getParams().get(0));
            subscriber = new Subscriber(idSub);
            System.out.println("Subscriber log in!");
            return;
        }

    }

    public void show(Command command) {
        //show services
        //show users [none, -b, -ib];

        if (command.getCommands().size() <= 1) {
            System.out.println("Error Command SHOW par");
            return;
        }
        String secondWord = command.getCommands().get(1);

        try {
            if (secondWord.equals(Commands.SERVICE)) {
                if(admin == null) displayList(subscriber.getAllServicesList());
                else  displayList(admin.getListAllServices());
                return;
            }
            if (secondWord.equals(Commands.USER)) {
                if (command.getParams().size() == 0) {
                    displayList(admin.getListAllUser());
                    return;
                }
                if (command.getParams().get(0).equals(Commands.BLOCKED)) {
                    displayList(admin.getListUserByStatus(false));
                    return;
                }
                if (command.getParams().get(0).equals(Commands.UNBLOCKED)) {
                    displayList(admin.getListUserByStatus(true));
                    return;
                }
            }
            if(secondWord.equals(Commands.INFO)){
                System.out.println(subscriber.getUser());
                return;
            }
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("Error args");
        } catch (NullPointerException E) {
            System.out.println("Admin dont sing in!");
        }  catch (Exception E){
            System.out.println("Error Command Show par");
        }

        System.out.println("Error Command SHOW par");
    }

    public void add(Command command) {
        //add user (name, balance, status)
        //add money [money]
        if (command.getCommands().size() <= 1) {
            System.out.println("Error Command Add par");
            return;
        }
        String secondWord = command.getCommands().get(1);

        // ADD USER [ int int int ]
        if (secondWord.equals(Commands.USER)) {
            addUser(command);
            return;
        }
        // add money [ money ]
        if (secondWord.equals(Commands.MONEY)) {
            addMoney(command);
            return;
        }
        if(secondWord.equals(Commands.SERVICE)){
            addService(command);
            return;
        }
        System.out.println("Error Command Add ");
    }

    public void update(Command command) {
        // "UPDATE STATUS [id user] [state] - change state (block/unblock) for user num 'id'" +
        try {
            String secondWord = command.getCommands().get(1);
            if(secondWord.equals(Commands.STATUS)){
                int idUser = Integer.parseInt(command.getParams().get(0));
                boolean state = Boolean.getBoolean(command.getParams().get(1));
                admin.updateStatusUserById(idUser, state);
                return;
            }
        }catch (NullPointerException E) {
            System.out.println("User sing out!");
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("Error ADD MONEY param");
        } catch (NumberFormatException E) {
            System.out.println("Error ADD MONEY param");
        } catch (Exception E) {
            System.out.println("Eror ADD MONEY param");
        }

    }

    public void remove(Command command) {
        // user
        // DELETE SERVICE [ idService ] - disconnect id service"
        // admin
        // DELETE SERVICE [ idService ] - remove service "
        // DELETE SUB [ id ] - remove subscriber num 'id'

        try {
            String secondWord = command.getCommands().get(1);
            if (secondWord.equals(Commands.USER)) {
                int id = Integer.parseInt(command.getParams().get(0));
                admin.removeUserById(id);
                System.out.println("Remove user id = " + id + " success!");
                return;
            }
            if(secondWord.equals(Commands.SERVICE)) {
                int idService = Integer.parseInt(command.getParams().get(0));
                if(admin != null) {
                    admin.removeService(idService);
                    System.out.println("Remove service id = " + idService + " success!");
                    return;
                }else if (subscriber != null){
                    subscriber.disconnectService(idService);
                    System.out.println("Disconnect service id = " + idService + " success!");
                    return;
                }
            }
        } catch (NullPointerException E) {
            System.out.println("no one don`t sing out!");
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("Error ADD MONEY param");
        } catch (NumberFormatException E) {
            System.out.println("Error ADD MONEY param");
        } catch (Exception E) {
            System.out.println(E);
        }
        System.out.println("Eror REMOVE SERVICE param");

    }

    public String getHelp() {
        return "HELP - display this help\n" +
                "LOG ADMIN - log in as Admin\n" +
                "LOG SUB [ id ] - log in as User\n" +
                "\n\t\tAdmin:\n" +
                "SHOW SUB [ non, -b, -ub ] - display list of users (all, block, unblock)\n" +
                "SHOW SERVICES - display list of services\n" +
                "ADD SUB [ service, balance, status ] - add new user with parameters\n" +
                "ADD SERVICE [ name price ]\n" +
                "DELETE SUB [id] - delete user num 'id'\n" +
                "UPDATE STATUS [id user state] - change state (block/unblock) for user num 'id'\n" +
                "\n\tSUBSCRIBER\t\n" +
                "ADD MONEY [money] - add money to balance\n" +
                "ADD SERVICE [ idService ]- add service t\n" +
                "MAKE CALL - make call\n"+
                "DELETE SERVICE [ idService ] - remove service\n" +
                "SHOW INFO - show info about user\n" +
                "SHOW SERVICES - display list of services\n";
    }

    public void make(Command command) {
        try {
            String secondWord = command.getCommands().get(1);
            if (secondWord.equals(Commands.CALL)) {
                subscriber.makeCall();
                System.out.println("Make call success!");
                return;
            }
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("Error args");
        } catch (NullPointerException E) {
            System.out.println("Admin dont sing in!");
        }  catch (Exception E){
            System.out.println("Error Command Make Call par");
        }
        System.out.println("Error command");
    }

    private void displayList(List list){
        for(Object object : list){
            System.out.println(object);
        }
    }
    private void addMoney(Command command) {
        //add money [ 999 ]
        try {
            int money = Integer.parseInt(command.getParams().get(0));
            if (money < 0)
                throw new NumberFormatException();
            subscriber.addMoney(money);
            System.out.println("User id= " + subscriber.getUser().getName() + " add " + money);
            System.out.println("Balance=" + subscriber.getUser().getBalance());
        } catch (NullPointerException E) {
            System.out.println("User sing out!");
        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("Error ADD MONEY param");
        } catch (NumberFormatException E) {
            System.out.println("Error ADD MONEY param");
        } catch (Exception E) {
            System.out.println("Error ADD MONEY param");
        }
    }
    private void addUser(Command command) {
        // add user ( 'name', balance, status )
        try {
            String name = command.getParams().get(0);
            int balance = Integer.parseInt(command.getParams().get(1));
            boolean status = Boolean.getBoolean(command.getParams().get(2));
            User newUser = new User(name, balance, status);
            int newId = admin.addNewUser(newUser);
            newUser.setIdUser(newId);
            System.out.println("new User added!");
            System.out.println(newUser);
            return;
        }catch (ArrayIndexOutOfBoundsException E){
            System.out.println("Error add user parr");
        }catch (NullPointerException E) {
            System.out.println("Admin don`t log in!");
        }catch (Exception E) {
            System.out.println("Error ADD USER param");
        }
    }
    private void addService(Command command) {
        //log in as admin :
        //"ADD SERVICE [ 'name service' price ] - add new service to DB";
        //log in as user
        //"ADD SERVICE [ idService ] - connect user to new service";
        try {
            if(admin != null) {
                String name = command.getParams().get(0);
                int price = Integer.parseInt(command.getParams().get(1));

                Service newService = new Service(name, price);

                int newId = admin.addService(newService);
                newService.setIdService(newId);
                System.out.println("new User added!");
                System.out.println(newService);
                return;
            }
            else if(subscriber != null){
                int idService = Integer.parseInt(command.getParams().get(0));
                subscriber.connectService(idService);
                return;
            }

        } catch (ArrayIndexOutOfBoundsException E) {
            System.out.println("Error add user parr");
        } catch (NullPointerException E) {
            System.out.println("No one logged in!");
        } catch (Exception E) {
            System.out.println("Error ADD Service param");
        }
    }

}
