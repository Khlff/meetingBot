package commands;

import commands.Command;

import java.util.ArrayList;

public class CommandHandler {
    static ArrayList<Command> commandArrayList = new ArrayList<>();
    public void registerCommand(Command command){
        commandArrayList.add(command);
    }
    public ArrayList<Command> getCommandList(){return commandArrayList;}
}
