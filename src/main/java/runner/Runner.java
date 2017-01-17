package runner;
import util.HibernateUtil;
import java.util.*;

public class Runner {

    private RunnerFunction function;

    public Runner(RunnerFunction function) {
        this.function = function;
        HibernateUtil.getSessionAnnotationFactory();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        Command command = null;

        while (true) {
            System.out.printf("-> ");
            String input = sc.nextLine();
            if (input.equals(""))
                continue;

            command = new Command(input);
            command.commandToLoverCase();
            String first = command.getCommands().get(0);

            if (first.equals(Commands.ENTER))       function.singIn(command);
            else if (first.equals(Commands.HELP))   System.out.println(function.getHelp());
            else if (first.equals(Commands.SHOW))   function.show(command);
            else if (first.equals(Commands.ADD))    function.add(command);
            else if (first.equals(Commands.DELETE)) function.remove(command);
            else if (first.equals(Commands.UPDATE)) function.update(command);
            else if (first.equals(Commands.MAKE))   function.make(command);
            else if (first.equals(Commands.EXIT))   {
                HibernateUtil.getSessionAnnotationFactory().close();
                return;
            }
            else
                System.out.println("Error command");
        }
    }
}






