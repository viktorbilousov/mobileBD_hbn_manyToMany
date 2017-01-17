package runner;

public interface RunnerFunction {
   void singIn(Command commandLine);
   void show(Command commandLine);
   void add(Command commandLine);
   void update(Command commandLine);
   void remove(Command commandLine);
   void make(Command command);
   String getHelp();
}
