package runner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Command
{
    // command1 command2 .. commandN [ 'name param 1' 'name param 2' ... 'name param N' ]
    private ArrayList<String> commands = new ArrayList<String>();
    private ArrayList<String> params = new ArrayList<String>();

    public Command(String line){
        ArrayList<String> tmp = new ArrayList<String>();
        for(String word  : line.split(" "))
        {
            if(word.length() == 0) {
                continue;
            }
            tmp.add(word);
        }
        init(tmp);
    }
    public Command(ArrayList<String> input){
       init(input);
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void commandToUpperCase(){
        for(int i=0; i<commands.size(); i++){
            commands.set(i, commands.get(i).toUpperCase());
        }
    }

    public void commandToLoverCase(){
        for(int i=0; i<commands.size(); i++){
            commands.set(i, commands.get(i).toLowerCase());
        }
    }

    private void init(ArrayList<String> tmp){
        int indexStartParam = tmp.indexOf("[");
        int indexEndParam = tmp.indexOf("]");
        if(indexStartParam == -1 || indexEndParam == -1) {
            commands = tmp;
            return;
        }
        commands = new ArrayList<String>(tmp.subList(0 , indexStartParam));
        getParam(new ArrayList<String>(tmp.subList(indexStartParam + 1, indexEndParam )));

    }

    private void getParam(ArrayList<String> paramList){
        boolean newWord = true;
        String fullParam = ""; // 'example param'
        for(int i=0; i<paramList.size(); i++){
            String word = paramList.get(i);
             if (!word.startsWith("\'")
                     && word.length() >= 1
                     && newWord
                     ) {
                 // param (not 'param' and not 'example param')
                 if(!word.equals(" "))
                    params.add(word);
                 continue;
             }
             newWord = false;
             fullParam += word;
             if(fullParam.endsWith("\'") && fullParam.length() > 1){
                 params.add(fullParam.substring(1, fullParam.length()-1));
                 fullParam = "";
                 newWord = true;
             }else {
                 fullParam += " ";
             }
         }
    }

    @Override
    public String toString() {
        String line  = "-------------commands-------------\n";
        for(String s : commands){
            line += s + "\n";
        }
        line += "-------------params-------------";
        for(String s : params){
            line += "\n" + s;
        }
        return line;
    }
}